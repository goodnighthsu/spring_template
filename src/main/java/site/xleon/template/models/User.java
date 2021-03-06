package site.xleon.template.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author leon xu
 * @date 2021/5/29 11:01 下午
 */
@Data
@TableName("user")
public class User extends BaseEntity {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private String account;
  private String password;
  private String mobile;
  private Integer roleId;
  private Integer state;

  @TableField(exist = false)
  private String token;
}
