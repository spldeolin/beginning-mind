package com.spldeolin.beginningmind.ancestor;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.spldeolin.beginningmind.extension.dto.RequestTrack;

/**
 * 所有ReqDto的直接或间接接口
 *
 * @author Deolin 2020-11-22
 */
public interface ReqDtoAncestor {

    @JsonAnySetter
    default void set(String name, Object value) {
        RequestTrack.getCurrent().getUnrecognizedRequestBodyProperties().put(name, value);
    }

}