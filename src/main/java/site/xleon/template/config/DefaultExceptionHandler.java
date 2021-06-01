/*
 * @Author: your name
 * @Date: 2021-02-05 15:57:22
 * @LastEditTime: 2021-03-10 14:35:16
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /study/src/main/java/site/xleon/core/DefaultExceptionHandler.java
 */
package site.xleon.template.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;

//import com.example.study.mapper.ErrorMapper;
//import com.example.study.models.ErrorModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import site.xleon.template.core.MyException;
import site.xleon.template.core.Result;
import site.xleon.template.core.ResultCodeEnum;
import site.xleon.template.core.Utils;

// 异常处理
@Controller
@ControllerAdvice
public class DefaultExceptionHandler extends BasicErrorController {
//    @Autowired
//    ErrorMapper errorMapper;
    // 容器异常处理
    public DefaultExceptionHandler() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    /**
     * 容器异常处理
     */
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        System.out.println("Container Error: ");
        Map<String, Object> body = this.getErrorAttributes(request, ErrorAttributeOptions.of(
                ErrorAttributeOptions.Include.MESSAGE,
                ErrorAttributeOptions.Include.EXCEPTION,
                ErrorAttributeOptions.Include.STACK_TRACE,
                ErrorAttributeOptions.Include.BINDING_ERRORS
        ));
        HttpStatus status= getStatus(request);
        String error = (String)body.get("error");

        // 应用异常已处理
        if (error.equals("OK")){
            return null;
        }

        Result<Null> result;
        if (status.value() >= 400 ){
            result = Result.fail(error);
//            logError(result, request, "http " + String.valueOf(status.value()));
        }else{
            String message = (String)body.get("message");
            result = Result.fail(error + ": " +  message);
//            logError(result, request, null);
        }


        return new ResponseEntity<>(Utils.objectToMap(result), status);
    }

    /**
     * 应用异常处理
     * @param {Exception}
     * @return {Result}
     */
    @ResponseBody
    @ExceptionHandler
    public Result defaultException(HttpServletRequest request, Exception exception) {
        System.out.println("default exception: ");
        if (
                exception instanceof MethodArgumentTypeMismatchException ||
                exception instanceof MissingServletRequestParameterException ||
                exception instanceof HttpRequestMethodNotSupportedException ||
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

        //
//    logError(result, request, exception.getClass().getName());
        return Result.fail(ResultCodeEnum.ERROR, exception.getMessage(), exception);
    }

    /**
     * 异常写入数据库
     * @param result
     */
//    private void logError(Result result, HttpServletRequest request, String exceptionClassName) {
//        Integer code = (Integer)result.get("code");
//        String message = (String)result.get("message");
//        Object data = result.get("data");
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString;
//        try {
//            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
//        }catch (Exception exception){
//            jsonString = exception.getMessage();
//        }
//
//        // 成功不记录
//        if (code >= YDCode.SUCCESS.getCode()){
//            return;
//        }
//
//        ErrorModel error = new ErrorModel();
//        // code
//        YDCode.init(code).ifPresent(value -> error.setCode(value.getName()));
//        // url
//        HttpStatus status= getStatus(request);
//        if (status == HttpStatus.NOT_FOUND) {
//            Map<String, Object> errorAttributes = this.getErrorAttributes(request, ErrorAttributeOptions.defaults());
//            String urlString = request.getScheme() +
//                    "://" + request.getServerName() +
//                    ":" + request.getServerPort() +
//                    errorAttributes.get("path");
//            error.setUrl(urlString);
//        }else{
//            error.setUrl(request.getRequestURL().toString());
//        }
//
//        // method
//        error.setMethod(request.getMethod());
//
//        // header
//        HashMap<String, String> headers = new HashMap<>();
//        Enumeration<String> headersNames = request.getHeaderNames();
//        while (headersNames.hasMoreElements()) {
//            String name = headersNames.nextElement();
//            headers.put(name, request.getHeader(name));
//        }
//        error.setHeader(Util.jsonString(headers).orElse(null));
//
//        // parameter
//        HashMap<String, String> params = new HashMap<>();
//        Enumeration<String> paramNames = request.getParameterNames();
//        while (paramNames.hasMoreElements()) {
//            String name = paramNames.nextElement();
//            params.put(name, request.getParameter(name));
//        }
//        if (params.size() != 0) {
//            error.setParam(Util.jsonString(params).orElse(null));
//        }
//
//        // body exception message stack
//        error.setBody(getBody(request).orElse(null));
//        error.setException(exceptionClassName);
//        error.setMessage(message);
//        error.setStack(jsonString);
//
//        // ip
//        error.setIp(request.getRemoteAddr());
//
//        // 已被全局异常处理, 不可继续throw
//        try {
//            Integer count = errorMapper.insert(error);
//            if (count == 0 ) {
//                // TODO: sql异常写入文本Log
//                System.out.println("Log error failure!!!");
//            }
//        }catch(Exception exception) {
//            // TODO: sql异常写入文本Log
//            System.out.println("Log Error: " + exception.getMessage());
//        }
//    }

    /**
     * 获取请求内的body
     * @param request
     * @return
     */
    public Optional<String> getBody(HttpServletRequest request){
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String line = null;
            while ( (line = streamReader.readLine()) != null) {
                stringBuffer.append(line);
            }
        }catch(Exception exception) {
            //
        }
        String result = stringBuffer.toString();
        if (result.length() > 0){
            return Optional.of(result);
        }

        return Optional.ofNullable(null);
    }
}
