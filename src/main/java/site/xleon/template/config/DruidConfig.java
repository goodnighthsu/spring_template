package site.xleon.template.config;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ali druid
 * @author leon xu
 * @date 2021/6/4 2:00 下午
 */
@Configuration
public class DruidConfig {
  public Slf4jLogFilter logFilter () {

    Slf4jLogFilter logFilter = new Slf4jLogFilter();
    logFilter.setStatementExecutableSqlLogEnable(true);
    logFilter.setStatementLogEnabled(false);
    return logFilter;
  }

  @Bean
  public StatFilter statFilter () {

    StatFilter statFilter = new StatFilter();
    statFilter.setSlowSqlMillis(3000);
    statFilter.setLogSlowSql(true);
    statFilter.setMergeSql(true);
    return statFilter;
  }

  /**
   * sql防火墙过滤器配置
   * @param wallConfig cofig
   * @return wall filter
   */
  @Bean
  public WallFilter wallFilter (WallConfig wallConfig) {

    WallFilter wallFilter = new WallFilter();
    wallFilter.setConfig(wallConfig);
    // 对被认为是攻击的SQL进行LOG.error输出
    wallFilter.setLogViolation(true);
    // 对被认为是攻击的SQL抛出SQLException
    wallFilter.setThrowException(false);
    return wallFilter;
  }

  /**
   * sql防火墙配置
   * @return wall config
   */
  @Bean
  public WallConfig wallConfig () {

    WallConfig wallConfig = new WallConfig();
    wallConfig.setAlterTableAllow(false);
    wallConfig.setCreateTableAllow(false);
    wallConfig.setDeleteAllow(false);
    wallConfig.setMergeAllow(false);
    wallConfig.setDescribeAllow(false);
    wallConfig.setShowAllow(false);
    return wallConfig;
  }
}
