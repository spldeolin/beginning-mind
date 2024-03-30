package com.spldeolin.beginningmind.app.enums;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.spldeolin.satisficing.framework.api.EnumAncestor;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Any modifications may be overwritten by future code generations.
 *
 * @author Deolin 2024-02-20
 */
@Getter
@AllArgsConstructor
public enum OfficesStateEnum implements EnumAncestor<String> {

    CA("CA", "ca"), MA("MA", "ma"), NY("NY", "ny"), ChiyodaKu("Chiyoda-Ku", "chiyoda");

    @JsonValue
    private final String code;

    private final String title;

    /**
     * 判断参数code是否是一个有效的枚举
     */
    public static boolean valid(String code) {
        return Arrays.stream(values()).anyMatch(anEnum -> anEnum.getCode().equals(code));
    }

    /**
     * 获取code对应的枚举
     */
    @JsonCreator
    public static OfficesStateEnum of(String code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return asJavabean().toString();
    }
}
