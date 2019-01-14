package com.spldeolin.beginningmind.core.aspect.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.spldeolin.beginningmind.core.constant.ResultCode;
import lombok.Data;

/**
 * 控制器通用返回类型
 *
 * @author Deolin
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestResult implements Serializable {

    private Integer code;

    private Object data;

    private String message;

    private static final long serialVersionUID = 1L;

    private RequestResult() {
    }

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

    public static RequestResult failure(ResultCode code, Object data, String message) {
        RequestResult instance = failure(code, message);
        instance.setData(data);
        return instance;
    }

}
