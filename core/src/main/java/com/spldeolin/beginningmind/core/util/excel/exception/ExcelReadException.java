package com.spldeolin.beginningmind.core.util.excel.exception;

import java.util.List;
import com.spldeolin.beginningmind.core.util.excel.entity.Invalid;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 额外校验未通过异常，由额外注解处理切面抛出
 *
 * @author Deolin
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExcelReadException extends Exception {

    private static final long serialVersionUID = 1L;

    private List<Invalid> parseInvalids;

    public ExcelReadException(List<Invalid> parseInvalids) {
        super();
        this.parseInvalids = parseInvalids;
    }

}