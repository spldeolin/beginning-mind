package com.spldeolin.beginningmind.aspect.dto;

import lombok.Builder;
import lombok.Data;

/**
 * （控制层）校验未通过的信息
 *
 * @author Deolin
 */
@Data
@Builder
public class Invalid {

    private String name;

    private Object value;

    private String cause;

}
