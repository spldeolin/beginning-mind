package com.spldeolin.beginningmind.extension.javabean;

import com.spldeolin.beginningmind.stderr.CommonStderrEnum;
import lombok.Data;

/**
 * 控制器通用返回类型
 *
 * @author Deolin
 */
@Data
public class RequestResult<T> {

    private String code;

    private T data;

    private String errorMessage;

    public static <T> RequestResult<T> success() {
        return success(null);
    }

    public static <T> RequestResult<T> success(T data) {
        RequestResult<T> instance = new RequestResult<>();
        instance.setCode(CommonStderrEnum.OK.getCode());
        instance.setData(data);
        return instance;
    }

    public static <T> RequestResult<T> failure(String code, String errorMessage) {
        RequestResult<T> instance = new RequestResult<>();
        instance.setCode(code);
        instance.setErrorMessage(errorMessage);
        return instance;
    }

}
