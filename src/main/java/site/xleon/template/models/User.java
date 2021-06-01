package site.xleon.template.models;

import lombok.Data;

/**
 * @author leon xu
 * @date 2021/5/29 11:01 下午
 */
@Data
public class User {
    private String id;
    private String account;
    private String password;
    private String mobile;

    private String token;
}
