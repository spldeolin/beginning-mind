package com.spldeolin.beginningmind.core.aspect.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * （控制层）校验未通过的信息
 *
 * @author Deolin
 */
@Data
public class Invalid implements Serializable {

    private String name;

    private Object value;

    private String cause;

    private static final long serialVersionUID = 1L;

    public Invalid(String name, Object value, String cause) {
        this.name = name;
        this.value = value;
        this.cause = cause;
    }

}
