package com.spldeolin.beginningmind.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spldeolin.beginningmind.constant.ResultCode;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 控制器通用返回类型
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
        instance.setCode(ResultCode.OK.getCode());
        instance.setData(data);
        return instance;
    }

    public static RequestResult failure(ResultCode code) {
        return failure(code, code.getDefaultMessage());
    }

    public static RequestResult failure(ResultCode code, String message) {
        RequestResult instance = new RequestResult();
        instance.setCode(code.getCode());
        instance.setMessage(message);
        return instance;
    }

}
