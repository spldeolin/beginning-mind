package com.spldeolin.beginningmind.enums;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.spldeolin.beginningmind.ancestor.EnumAncestor;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 控制器通用返回值的结果码
 * <pre>
 *   200 请求成功，返回预想结果
 *   400 请求方法交互失败
 *   401 没有登录
 *   403 登录者没有权限
 *   404 404 Not Found
 *   500 捕获到未考虑到的异常
 *  1001 存在业务错误，无法返回预想结果（用户名被占用、库存不足 等）
 *  </pre>
 *
 * @author Deolin 2020-11-08
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum implements EnumAncestor<Integer> {

    OK(200, ""),

    BAD_REQEUST(400, "交互错误"),

    UNSIGNED(401, "未登录或登录超时"),

    FORBIDDEN(403, "权限不足"),

    NOT_FOUND(404, "Not Found"),

    INTERNAL_ERROR(500, "权限不足"),

    SERVICE_ERROR(1001, "业务异常"),

    ;

    @JsonValue
    private final Integer code;

    private final String title;

    /**
     * 判断参数code是否是一个有效的枚举
     */
    public static boolean valid(Integer code) {
        return Arrays.stream(values()).anyMatch(anEnum -> anEnum.getCode().equals(code));
    }

    /**
     * 获取code对应的枚举
     */
    @JsonCreator
    public static ResultCodeEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return asJavabean().toString();
    }

}