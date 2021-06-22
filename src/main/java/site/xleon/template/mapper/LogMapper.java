package site.xleon.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import site.xleon.template.models.LogEntity;

/**
 * @author leon xu
 * @date 2021/6/17 1:41 下午
 */
@Mapper
@Repository
public interface LogMapper extends BaseMapper<LogEntity> {
}
