package com.spldeolin.beginningmind.core.excel;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 额外校验未通过异常，由额外注解处理切面抛出
 *
 * @author Deolin
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ParseInvalidException extends Exception {

    private static final long serialVersionUID = 1L;

    private List<ParseInvalid> parseInvalids;

}