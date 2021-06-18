/*
 * @Author: your name
 * @Date: 2021-02-18 17:56:54
 * @LastEditTime: 2021-02-25 12:57:24
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /study/src/main/java/com/example/study/config/MyBatisPlusConfig.java
 */
package site.xleon.template.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis plus config
 * @author leon xu
 * @date 2021/6/17 1:42 下午
 */
@Configuration
public class MyBatisPlusConfig {

  /**
   * 分页插件
   * @return interceptor
   */
  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor() {
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
  }
}
