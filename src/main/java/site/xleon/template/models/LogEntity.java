package site.xleon.template.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author leon xu
 * @date 2021/6/17 1:27 下午
 */
@Data
@TableName("log")
public class LogEntity extends BaseEntity {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private String url;
  private String method;
  private String headers;
  private String params;
  private String body;
  private String response;
  private Integer userId;
  private String remoteAddress;
  private String remoteUser;
  private String remoteHost;

  @TableField(exist = false)
  private User user;
}
