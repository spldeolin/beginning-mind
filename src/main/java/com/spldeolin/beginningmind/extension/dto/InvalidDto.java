package com.spldeolin.beginningmind.extension.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * （控制层）校验未通过的信息
 *
 * @author Deolin
 */
@Data
@Accessors(chain = true)
public class InvalidDto {

    private String path;

    private Object value;

    private String reason;

}
