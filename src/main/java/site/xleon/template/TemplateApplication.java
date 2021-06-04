package site.xleon.template;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author leon
 */
@SpringBootApplication
public class TemplateApplication {

  public static void main(String[] args) {
    SpringApplication.run(TemplateApplication.class, args);
  }

  /**
   * @description 注册一个StatViewServlet,进行druid监控页面配置
   * @return servlet registration bean
   */
  @Bean
  public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
    //先配置管理后台的servlet，访问的入口为/druid/
    ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>(
      new StatViewServlet(), "/druid/*");
    // IP白名单 (没有配置或者为空，则允许所有访问)
    servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
    // IP黑名单 (存在共同时，deny优先于allow)
    servletRegistrationBean.addInitParameter("deny", "");
    servletRegistrationBean.addInitParameter("loginUsername", "admin");
    servletRegistrationBean.addInitParameter("loginPassword", "admin");
    servletRegistrationBean.addInitParameter("resetEnable", "false");
    return servletRegistrationBean;
  }

  /**
   * @description 注册一个过滤器，允许页面正常浏览
   * @return filter registration bean
   */
  @Bean
  public FilterRegistrationBean<WebStatFilter> druidStatFilter(){
    FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(
      new WebStatFilter());
    // 添加过滤规则.
    filterRegistrationBean.addUrlPatterns("/*");
    // 添加不需要忽略的格式信息.
    filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
    return filterRegistrationBean;
  }
}

