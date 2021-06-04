package site.xleon.template.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author leon xu
 * @date 2021/6/2 1:31 下午
 */
@AllArgsConstructor
public enum StateEnum {
  // defines
  DISABLE(0, "DISABLE"),
  ENABLE(1, "ENABLE");

  @Getter
  private final Integer value;
  @Getter
  private final String label;
}
