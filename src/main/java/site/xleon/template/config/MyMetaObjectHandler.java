package site.xleon.template.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author leon xu
 * @date 2021/6/23 12:35 下午
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
  @Override
  public void insertFill(MetaObject metaObject) {
    this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
    this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
  }
}
