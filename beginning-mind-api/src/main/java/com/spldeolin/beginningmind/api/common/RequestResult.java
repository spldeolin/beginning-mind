package com.spldeolin.beginningmind.api.common;

import lombok.Data;

/**
 * 控制器通用返回类型
 *
 * @author Deolin
 */
@Data
public class RequestResult<T> {

    /**
     * 结果码
     * <p>
     * 200请求成功；400外部错误；401未认证；403未授权；404 Not Found；500内部错误；599业务逻辑异常
     */
    private Integer code;

    private T data;

    private String errmsg;

    public static <T> RequestResult<T> success() {
        return success(null);
    }

    public static <T> RequestResult<T> success(T data) {
        RequestResult<T> instance = new RequestResult<>();
        instance.setCode(200);
        instance.setData(data);
        return instance;
    }

    public static <T> RequestResult<T> failure(Integer code, String errmsg) {
        RequestResult<T> instance = new RequestResult<>();
        instance.setCode(code);
        instance.setErrmsg(errmsg);
        return instance;
    }

}
