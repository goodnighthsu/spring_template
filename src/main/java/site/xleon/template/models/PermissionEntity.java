package site.xleon.template.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author leon xu
 * @date 2021/6/2 11:08 上午
 */
@Data
@TableName("permission")
public class PermissionEntity extends BaseEntity {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private String title;
  private String detail;
  private Integer parentId;
  private Boolean isMenu;
  private Integer seq;
  private String api;
  private String apiMethod;
  private Integer state;
}
