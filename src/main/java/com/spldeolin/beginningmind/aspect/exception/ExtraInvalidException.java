package com.spldeolin.beginningmind.aspect.exception;

import java.util.List;
import com.spldeolin.beginningmind.aspect.dto.Invalid;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 额外校验未通过异常，由额外注解处理切面抛出
 *
 * @author Deolin
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ExtraInvalidException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private List<Invalid> invalids;

}