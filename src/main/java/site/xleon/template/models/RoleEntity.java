package site.xleon.template.models;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author leon xu
 * @date 2021/6/2 11:12 上午
 */
@Data
@TableName("role")
public class RoleEntity {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  private String title;
  private Integer state;
}
