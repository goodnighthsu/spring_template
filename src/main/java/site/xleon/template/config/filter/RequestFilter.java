package site.xleon.template.config.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author leon xu
 * @date 2021/6/22 9:27 上午
 */
public class RequestFilter implements Filter {
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    ServletRequest requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
    filterChain.doFilter(requestWrapper, servletResponse);
  }
}


