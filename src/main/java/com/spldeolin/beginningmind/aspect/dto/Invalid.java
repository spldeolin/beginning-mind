package com.spldeolin.beginningmind.aspect.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * （控制层）校验未通过的信息
 *
 * @author Deolin
 */
@Data
@AllArgsConstructor
@Builder
public class Invalid {

    private String name;

    private Object value;

    private String cause;

}
