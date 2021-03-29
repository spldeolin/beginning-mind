package com.spldeolin.beginningmind.extension.javabean;

import com.spldeolin.beginningmind.enums.ResultCodeEnum;
import lombok.Data;

/**
 * 控制器通用返回类型
 *
 * @author Deolin
 */
@Data
public class RequestResult<T> {

    private ResultCodeEnum code;

    private T data;

    private String errmsg;

    public static <T> RequestResult<T> success() {
        return success(null);
    }

    public static <T> RequestResult<T> success(T data) {
        RequestResult<T> instance = new RequestResult<>();
        instance.setCode(ResultCodeEnum.OK);
        instance.setData(data);
        return instance;
    }

    public static <T> RequestResult<T> failure(ResultCodeEnum code) {
        return failure(code, null);
    }

    public static <T> RequestResult<T> failure(ResultCodeEnum code, String message) {
        RequestResult<T> instance = new RequestResult<>();
        instance.setCode(code);
        instance.setErrmsg(message);
        return instance;
    }

}
