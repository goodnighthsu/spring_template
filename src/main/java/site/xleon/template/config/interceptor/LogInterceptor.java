package site.xleon.template.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import site.xleon.template.core.JWT;
import site.xleon.template.core.MyException;
import site.xleon.template.mapper.LogMapper;
import site.xleon.template.models.LogEntity;
import site.xleon.template.config.filter.RequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 访问日志
 * @author leon xu
 * @date 2021/5/30 9:16 下午
 */
@Component
public class LogInterceptor implements HandlerInterceptor {
  private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

  final LogMapper logMapper;

  public LogInterceptor(LogMapper logMapper) {
    this.logMapper = logMapper;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    LogEntity log = new LogEntity();
    log.setUrl(request.getRequestURI());
    log.setMethod(request.getMethod());

    // header
    Map<String, String> headers = new HashMap<>();
    Enumeration<String> headersNames = request.getHeaderNames();
    while (headersNames.hasMoreElements()) {
      String name = headersNames.nextElement();
      headers.put(name, request.getHeader(name));
    }
    log.setHeaders(headers.toString());

    // params
    HashMap<String, String> params = new HashMap<>();
    Enumeration<String> paramNames = request.getParameterNames();
    while (paramNames.hasMoreElements()) {
      String name = paramNames.nextElement();
      params.put(name, request.getParameter(name));
    }
    if (!params.isEmpty()) {
      log.setParams(params.toString());
    }

    // body
    log.setBody(new RequestWrapper(request).getBodyString());

    Integer userId = 0;
    try {
      userId = JWT.getUserId(request);
    }catch (MyException exception){
      // allow invalid token
    }
    log.setUserId(userId);
    //
    log.setRemoteAddress(request.getRemoteAddr());
    log.setRemoteHost(request.getRemoteHost());
    log.setRemoteUser(request.getRemoteUser());


    int count = logMapper.insert(log);
    if (count != 1) {
      logger.error("insert log failure");
    }

    // TODO: request dur

    return true;
  }
}
