package site.xleon.template.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.xleon.template.config.filter.RequestFilter;

/**
 * @author leon xu
 * @date 2021/6/22 9:46 上午
 */
@Configuration
public class FilterConfig {
  @Bean
  public FilterRegistrationBean<RequestFilter> registrationBean() {
    FilterRegistrationBean<RequestFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new RequestFilter());
    registration.addUrlPatterns("/*");
    return registration;
  }
}
