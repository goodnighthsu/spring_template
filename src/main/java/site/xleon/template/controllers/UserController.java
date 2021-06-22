package site.xleon.template.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import site.xleon.template.core.JWT;
import site.xleon.template.core.MyException;
import site.xleon.template.core.Result;
import site.xleon.template.mapper.UserMapper;
import site.xleon.template.models.User;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author leon xu
 * @date 2021/5/29 10:40 下午
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController extends BaseController {
  private UserMapper userMapper;

  @PostMapping("/login")
  public Result<User> login(@RequestBody @Valid LoginParams params) throws MyException {
    QueryWrapper<User> query = new QueryWrapper<>();
    query.select("id")
      .eq("account", params.getAccount())
      .eq("password", params.getPassword());
    User user = userMapper.selectOne(query);
    if (user == null) {
      throw new MyException("account or password invalid");
    }
    user.setToken(new JWT().createTokenByUser(user));
    return Result.success(user);
  }

  @GetMapping("/{id}")
  public Result<User> getUserDetail(@PathVariable String id) throws MyException {

    User user = new User();
    user.setId(Integer.getInteger(id));
    user.setMobile("1391234");

    return Result.success(user);
  }

  @Data
  private static class LoginParams {
    @NotBlank
    private String account;
    @NotBlank
    private String password;
  }

}
