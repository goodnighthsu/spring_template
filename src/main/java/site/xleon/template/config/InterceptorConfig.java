package site.xleon.template.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.xleon.template.config.interceptor.AuthenticationInterceptor;
import site.xleon.template.config.interceptor.LogInterceptor;
import site.xleon.template.mapper.LogMapper;

/**
 * 接口拦截
 *
 * @author leon xu
 * @date 2021/5/30 7:58 下午
 */
@Configuration
@AllArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
  private LogMapper logMapper;
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LogInterceptor(logMapper))
      .addPathPatterns("/**")
      .excludePathPatterns("/log/**", "/error");

    registry.addInterceptor(new AuthenticationInterceptor())
      .addPathPatterns("/**")
      .excludePathPatterns("/user/login/**");
  }
}
