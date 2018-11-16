package com.spldeolin.beginningmind.core.aspect.exception;

import java.util.Map;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 代表切面外层发生的异常，往往是filter中的异常
 *
 * 由BasicErrorController产生后，由ErrorControllerAspect解析并抛出，最终交给统一异常处理
 *
 * @author Deolin 2018/05/17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BasicErrorControllerOthersException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @see AbstractErrorController#getErrorAttributes(javax.servlet.http.HttpServletRequest, boolean)
     */
    private Map<String, Object> errorAttributes;

}
