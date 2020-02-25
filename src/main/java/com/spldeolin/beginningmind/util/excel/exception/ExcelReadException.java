package com.spldeolin.beginningmind.util.excel.exception;

import java.util.List;
import com.spldeolin.beginningmind.util.excel.entity.Invalid;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 读取Excel时发生异常
 *
 * @author Deolin 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExcelReadException extends Exception {

    private List<Invalid> parseInvalids;

    public ExcelReadException(ExcelCellContentInvalidException e) {
        parseInvalids = e.getParseInvalids();
    }

    public ExcelReadException(Exception e) {
        super(e);
    }

    public ExcelReadException(String message) {
        super(message);
    }

    private static final long serialVersionUID = 3812539018260837051L;

}
