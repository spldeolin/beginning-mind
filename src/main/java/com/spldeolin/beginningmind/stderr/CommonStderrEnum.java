package com.spldeolin.beginningmind.stderr;

import java.util.Arrays;
import javax.annotation.Nullable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.spldeolin.beginningmind.ancestor.EnumAncestor;
import com.spldeolin.beginningmind.ancestor.StderrAncestor;
import lombok.AllArgsConstructor;

/**
 * 通用标准异常
 *
 * @author Deolin 2021-08-17
 */
@AllArgsConstructor
public enum CommonStderrEnum implements StderrAncestor, EnumAncestor<String> {

    OK("c200", ""),

    BAD_REQEUST("c400", "外部错误"),

    UNSIGNED("c401", "未登录或登录超时"),

    FORBIDDEN("c403", "权限不足"),

    NOT_FOUND("c404", "Not Found"),

    INTERNAL_ERROR("c500", "内部错误"),

    ;

    @JsonValue
    private final String code;

    private final String errorMessage;

    @Override
    public String code() {
        return code;
    }

    @Nullable
    @Override
    public String errorMessage() {
        return errorMessage;
    }

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
    public static CommonStderrEnum of(String code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return asJavabean().toString();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTitle() {
        return errorMessage;
    }

}