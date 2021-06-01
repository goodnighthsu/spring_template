package site.xleon.template.controllers;

import lombok.Data;
import org.springframework.web.bind.annotation.*;
import site.xleon.template.core.JWT;
import site.xleon.template.core.MyException;
import site.xleon.template.core.Result;
import site.xleon.template.models.User;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author leon xu
 * @date 2021/5/29 10:40 下午
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Data
    private static class LoginParams {
        @NotBlank
        private String account;
        @NotBlank
        private String password;
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody @Valid LoginParams params) {
        User user = new User();
        user.setAccount(params.getAccount());
        user.setPassword(params.getPassword());

        user.setId("1");
        user.setToken(new JWT().createTokenByUser(user));
        return Result.success(user);
    }

    @GetMapping("/{id}")
    public Result<User> getUserDetail(@PathVariable String id) throws MyException {
        User user = new User();
        user.setId(id);
        user.setMobile("1391234");

        System.out.println("action id: " + this.getUserId());
        return Result.success(user);
    }

}
