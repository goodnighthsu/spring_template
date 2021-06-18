package site.xleon.template.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import site.xleon.template.core.MyException;
import site.xleon.template.core.Result;
import site.xleon.template.core.ResultCodeEnum;
import site.xleon.template.core.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.util.Map;


/**
 * 异常处理
 * @author leon
 */
@Controller
@ControllerAdvice
public class DefaultExceptionHandler extends BasicErrorController {

  private static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

  /**
   * 容器异常处理
   */
  public DefaultExceptionHandler() {
    super(new DefaultErrorAttributes(), new ErrorProperties());
  }

  /**
   * 容器异常处理
   */
  @Override
  public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
    Map<String, Object> body = this.getErrorAttributes(request, ErrorAttributeOptions.of(
      ErrorAttributeOptions.Include.MESSAGE,
      ErrorAttributeOptions.Include.EXCEPTION,
      ErrorAttributeOptions.Include.STACK_TRACE,
      ErrorAttributeOptions.Include.BINDING_ERRORS
    ));
    HttpStatus status = getStatus(request);
    String error = (String) body.get("error");

    // 应用异常已处理
    if ("OK".equals(error)) {
      return null;
    }

    Result<Null> result;
    if (status.value() >= 400) {
      result = Result.fail(error);
      logger.warn("request url: " + request.getRequestURI());
      logger.warn("container error: {} ", result);
    } else {
      String message = (String) body.get("message");
      result = Result.fail(error + ": " + message);
      logger.error("request url: " + request.getRequestURI());
      logger.error("container error: {}",  message);
    }
    return new ResponseEntity<>(Utils.objectToMap(result), status);
  }

  /**
   * 应用异常处理
   * @param request request
   * @param exception exception
   * @return result
   */
  @ResponseBody
  @ExceptionHandler
  public Result<Object> defaultException(HttpServletRequest request, Exception exception) {
    logger.warn("request url: " + request.getRequestURI());
    if (exception instanceof MethodArgumentTypeMismatchException ||
        exception instanceof MissingServletRequestParameterException ||
//        exception instanceof HttpRequestMethodNotSupportedException ||
        exception instanceof HttpMessageConversionException ||
        exception instanceof MyException
    ) {
      return Result.fail(exception.getMessage());
    } else if (exception instanceof MethodArgumentNotValidException) {
      MethodArgumentNotValidException invalidException = (MethodArgumentNotValidException) exception;
      FieldError fieldError = invalidException.getBindingResult().getFieldError();
      assert fieldError != null;
      return Result.fail(fieldError.getField() + ": " + fieldError.getDefaultMessage());
    } else if (exception instanceof BindException) {
      BindException bindException = (BindException) exception;
      FieldError fieldError = bindException.getBindingResult().getFieldError();
      assert fieldError != null;
      return Result.fail(fieldError.getField() + ": " + fieldError.getDefaultMessage());
    }

    logger.error("request url: "  + request.getRequestURI());
    logger.error(exception.getMessage(), exception);
    return Result.fail(ResultCodeEnum.ERROR, exception.getMessage(), exception);
  }
}
