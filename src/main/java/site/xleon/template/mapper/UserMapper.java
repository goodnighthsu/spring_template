package site.xleon.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import site.xleon.template.models.User;

/**
 * @author leon xu
 * @date 2021/6/1 1:52 下午
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
