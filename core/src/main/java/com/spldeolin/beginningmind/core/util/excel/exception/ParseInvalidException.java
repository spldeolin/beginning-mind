package com.spldeolin.beginningmind.core.util.excel.exception;

import java.util.List;
import com.spldeolin.beginningmind.core.util.excel.entity.ParseInvalid;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 额外校验未通过异常，由额外注解处理切面抛出
 *
 * @author Deolin
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ParseInvalidException extends Exception {

    private static final long serialVersionUID = 1L;

    private List<ParseInvalid> parseInvalids;

    public ParseInvalidException(List<ParseInvalid> parseInvalids) {
        super();
        this.parseInvalids = parseInvalids;
    }

}