package site.xleon.template.config.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import site.xleon.template.config.DefaultExceptionHandler;
import site.xleon.template.core.JWT;
import site.xleon.template.core.MyException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leon xu
 * 认证拦截
 * @date 2021/5/30 8:04 下午
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod method = (HandlerMethod)handler;
        if (method.getBeanType().isAssignableFrom(DefaultExceptionHandler.class)) {
            return true;
        }
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            throw new MyException("token can not be null");
        }

        String userId = JWT.getUserId(token);
        return true;
    }
}
