package com.spldeolin.beginningmind.util.excel.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 缺省的单元格内容转换器不存在时，抛出一个一次。这是个BUG
 *
 * @author Deolin 2019-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DefaultCellConverterAbsentException extends RuntimeException {

    private static final long serialVersionUID = 8483561201723149884L;

    private String fieldType;

    private String fieldName;

    public DefaultCellConverterAbsentException() {
    }

    public DefaultCellConverterAbsentException(String message) {
        super(message);
    }

}
