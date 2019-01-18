package com.spldeolin.beginningmind.core.aspect.exception;

import java.util.List;
import com.spldeolin.beginningmind.core.aspect.dto.Invalid;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 额外校验未通过异常，由额外注解处理切面抛出
 *
 * @author Deolin
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExtraInvalidException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private List<Invalid> invalids;

    public ExtraInvalidException(List<Invalid> invalids) {
        this.invalids = invalids;
    }

}