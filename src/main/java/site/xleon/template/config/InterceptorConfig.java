package site.xleon.template.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import site.xleon.template.config.interceptor.AuthenticationInterceptor;
import site.xleon.template.config.interceptor.LogInterceptor;

/**
 * @author leon xu
 * @date 2021/5/30 7:58 下午
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/user/login/**");
        registry.addInterceptor(new LogInterceptor())
                .addPathPatterns("/**");
    }
}
