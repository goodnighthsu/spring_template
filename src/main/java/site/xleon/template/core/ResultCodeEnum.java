package site.xleon.template.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author leon xu
 * @date 2021/5/30 5:39 下午
 */
@AllArgsConstructor
public enum ResultCodeEnum {
    SUCCESS(1, " SUCCESS"),
    WARN(0, "WARN"),
    ERROR(-1, "ERROR");

    @Getter
    private final Integer value;
    private final String label;
}
