package com.spldeolin.beginningmind.core.aspect.exception;

import java.util.List;
import com.spldeolin.beginningmind.core.aspect.dto.Invalid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 额外校验未通过异常，由额外注解处理切面抛出
 *
 * @author Deolin
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ExtraInvalidException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private List<Invalid> invalids;

}