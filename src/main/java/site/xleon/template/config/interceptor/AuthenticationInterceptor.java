package site.xleon.template.config.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import site.xleon.template.config.DefaultExceptionHandler;
import site.xleon.template.core.JWT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证拦截
 *
 * @author leon xu
 * @date 2021/5/30 8:04 下午
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }

    HandlerMethod method = (HandlerMethod) handler;
    if (method.getBeanType().isAssignableFrom(DefaultExceptionHandler.class)) {
      return true;
    }

    // token 校验
    JWT.getUserId(request);
    return true;
  }
}
