package com.spldeolin.beginningmind.extension.javabean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spldeolin.beginningmind.enums.ResultCodeEnum;
import lombok.Data;

/**
 * 控制器通用返回类型
 *
 * @author Deolin
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestResult {

    private ResultCodeEnum code;

    private Object data;

    private String message;

    public static RequestResult success() {
        return success(null);
    }

    public static RequestResult success(Object data) {
        RequestResult instance = new RequestResult();
        instance.setCode(ResultCodeEnum.OK);
        instance.setData(data);
        return instance;
    }

    public static RequestResult failure(ResultCodeEnum code) {
        return failure(code, null);
    }

    public static RequestResult failure(ResultCodeEnum code, String message) {
        RequestResult instance = new RequestResult();
        instance.setCode(code);
        instance.setMessage(message);
        return instance;
    }

}
