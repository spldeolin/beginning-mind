package com.spldeolin.beginningmind.core.generator.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * @author Deolin 2018/11/14
 */

@Data
@Builder
public class ColumnDTO implements Serializable {

    private String name;

    private String comment;

    private String type;

    private Long length;

    private Boolean isTinyint1Unsigned;

    private static final long serialVersionUID = 1L;

}