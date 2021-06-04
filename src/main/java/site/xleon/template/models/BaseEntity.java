package site.xleon.template.models;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * @author leon xu
 * @date 2021/6/1 1:19 下午
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class BaseEntity {
  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  @JsonFormat(pattern = DATE_FORMAT, timezone = "GMT+8")
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;

  @JsonFormat(pattern = DATE_FORMAT, timezone = "GMT+8")
  @TableField(fill = FieldFill.UPDATE)
  private Date updateTime;

}
