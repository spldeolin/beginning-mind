package com.spldeolin.beginningmind.aspect.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.filter.dto.RequestTrackDTO;
import com.spldeolin.beginningmind.util.WebContext;
import lombok.Data;

/**
 * 控制器通用返回类型
 *
 * @author Deolin
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestResult {

    private Integer code;

    private Object data;

    private String message;

    private String insignia;

    private RequestResult() {
        setupCurrentInsignia();
    }

    private void setupCurrentInsignia() {
        RequestTrackDTO track = WebContext.getRequestTrack();
        if (track != null) {
            insignia = track.getInsignia();
        }
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
        return failure(code, null);
    }

    public static RequestResult failure(ResultCode code, String message) {
        RequestResult instance = new RequestResult();
        instance.setCode(code.getCode());
        instance.setMessage(message);
        return instance;
    }

}
