package com.spldeolin.beginningmind.core.generator.dto;

import java.io.Serializable;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Deolin 2018/11/14
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ColumnDTO implements Serializable {

    private String name;

    private String comment;

    private String type;

    private BigInteger length;

    private Boolean isTinyint1Unsigned;

    private static final long serialVersionUID = 1L;

}