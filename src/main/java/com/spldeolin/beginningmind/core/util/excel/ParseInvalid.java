package com.spldeolin.beginningmind.core.util.excel;

import lombok.Builder;
import lombok.Data;

/**
 * @author Deolin 2018/07/09
 */
@Data
@Builder
public class ParseInvalid {

    private String columnLetter;

    private Integer rowNumber;

    private String cause;

}
