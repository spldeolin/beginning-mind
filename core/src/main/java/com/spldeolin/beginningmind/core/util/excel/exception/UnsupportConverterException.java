package com.spldeolin.beginningmind.core.util.excel.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 由于没有指定Converter并且没有缺省的Converter，导致Excels无法使用，应该字段自定义并指定一个Converter
 *
 * @author Deolin 2019-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UnsupportConverterException extends RuntimeException {

    private String fieldType;

    private String fieldName;

    public UnsupportConverterException() {
    }

    public UnsupportConverterException(String message) {
        super(message);
    }

    private static final long serialVersionUID = 1L;

}
