package site.xleon.template.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import site.xleon.template.core.MyException;
import site.xleon.template.core.Result;
import site.xleon.template.core.enums.StateEnum;
import site.xleon.template.mapper.RoleMapper;
import site.xleon.template.models.RoleEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author leon xu
 * @date 2021/6/2 1:23 下午
 */
@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {
  private RoleMapper roleMapper;

  @PostMapping()
  public Result<RoleEntity> add(@RequestBody @Valid AddParams params) throws MyException {
    String title = params.title.trim();
    QueryWrapper<RoleEntity> query = new QueryWrapper<>();
    query.select("id").eq("title", title);
    RoleEntity oldRole = roleMapper.selectOne(query);
    if (oldRole != null) {
      throw new MyException("role " + title + " already exist");
    }

    RoleEntity role = new RoleEntity();
    role.setTitle(title);
    role.setState(params.getState().getValue());

    int count = roleMapper.insert(role);
    if (count != 1) {
      throw new MyException("add role failure");
    }

    return Result.success(role);
  }

  @GetMapping()
  public Result<List<RoleEntity>> list() {
    QueryWrapper<RoleEntity> query = new QueryWrapper<>();
    query.select("*");
    List<RoleEntity> data = roleMapper.selectList(query);
    return Result.success(data);
  }


  @Data
  private static class AddParams {
    @NotBlank
    private String title;
    private StateEnum state = StateEnum.DISABLE;
  }
}
