package com.spldeolin.beginningmind.restful.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 控制器通用返回类型
 * <pre>
 *  400 API交互失败
 *  401 没有认证        （不在演示范围中）
 *  403 没有权限        （不在演示范围中）
 *  500 内部BUG
 *  200 成功
 * 1001 成功，但是存在业务异常（不在演示范围中）
 *                            （e.g.:请求的资源已被删除、禁用等）
 * </pre>
 *
 * @author Deolin
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestResult {

    private Integer code;

    private Object data;

    private String message;

    private RequestResult() {}

    public static RequestResult success() {
        return success(null);
    }

    public static RequestResult success(Object data) {
        RequestResult instance = new RequestResult();
        instance.setCode(200);
        instance.setData(data);
        return instance;
    }

    public static RequestResult failture(Integer code, String message) {
        RequestResult instance = new RequestResult();
        instance.setCode(code);
        instance.setMessage(message);
        return instance;
    }

}
