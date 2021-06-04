package site.xleon.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import site.xleon.template.models.PermissionEntity;

/**
 * @author leon xu
 * @date 2021/6/2 11:15 上午
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionEntity> {
}
