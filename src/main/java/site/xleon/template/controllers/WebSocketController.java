package site.xleon.template.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author leon xu
 * @date 2021/6/9 3:45 下午
 */
@Controller
public class WebSocketController {
  @MessageMapping("hello")
  @SendTo("/topic/greetings")
  public void greeting(String message) {
    System.out.println(message);
  }
}
