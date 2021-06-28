package site.xleon.template.controllers;

import org.springframework.web.bind.annotation.ModelAttribute;
import site.xleon.template.core.JWT;
import site.xleon.template.core.MyException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author leon xu
 * @date 2021/6/1 11:50 上午
 */
public class BaseController {
  protected HttpServletRequest request;

  @ModelAttribute
  private void init(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
  }

  public Integer getUserId() throws MyException {
    return JWT.getUserId(request);
  }

}
