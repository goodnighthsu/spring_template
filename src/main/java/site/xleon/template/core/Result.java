package site.xleon.template.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leon xu
 * @date 2021/5/29 10:59 下午
 */
@Data
@AllArgsConstructor
public class Result<T> {
  private Integer code;
  private String message;
  private T data;

  public static <T> Result<T> success(T data) {
    return new Result<>(ResultCodeEnum.SUCCESS.getValue(), "SUCCESS", data);
  }

  public static <T> Result<T> fail(String message) {
    return new Result<>(ResultCodeEnum.WARN.getValue(), message, null);
  }

  public static <T> Result<T> fail(ResultCodeEnum code, String message, T data) {
    return new Result<>(code.getValue(), message, data);
  }
}
