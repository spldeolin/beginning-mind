package com.spldeolin.beginningmind.util.excel.exception;

import java.util.List;
import com.spldeolin.beginningmind.util.excel.entity.Invalid;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单元格数据格式非法
 *
 * @author Deolin
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExcelCellContentInvalidException extends Exception {

    private static final long serialVersionUID = 5289508701858765332L;

    private List<Invalid> parseInvalids;

    public ExcelCellContentInvalidException(List<Invalid> parseInvalids) {
        super();
        this.parseInvalids = parseInvalids;
    }

}