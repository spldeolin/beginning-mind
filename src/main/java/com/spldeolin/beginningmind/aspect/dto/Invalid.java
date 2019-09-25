package com.spldeolin.beginningmind.aspect.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * （控制层）校验未通过的信息
 *
 * @author Deolin
 */
@Data
@AllArgsConstructor
public class Invalid implements Serializable {

    private String name;

    private Object value;

    private String cause;

    private static final long serialVersionUID = 1L;

}
