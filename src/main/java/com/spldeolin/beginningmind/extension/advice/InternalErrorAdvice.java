package com.spldeolin.beginningmind.extension.advice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.google.common.base.Joiner;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.extension.dto.InvalidDto;
import com.spldeolin.beginningmind.extension.dto.RequestResult;
import com.spldeolin.beginningmind.extension.dto.RequestTrack;
import lombok.extern.log4j.Log4j2;

/**
 * 控制层Advice切面：内部BUG
 *
 * @author Deolin
 * @see ResultCode
 */
@RestControllerAdvice
@Log4j2
public class InternalErrorAdvice {

    /**
     * 500 通过类级@Validated 启用的参数校验
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public RequestResult handleMethodCallInvalidException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
        List<InvalidDto> invalids = new ArrayList<>();
        for (ConstraintViolation<?> cv : cvs) {
            String path = Joiner.on(".").join(cv.getRootBeanClass().getSimpleName(), cv.getPropertyPath().toString());
            InvalidDto invalid = new InvalidDto().setPath(path).setValue(cv.getInvalidValue())
                    .setReason(cv.getMessage());
            invalids.add(invalid);
        }
        log.error(invalids);
        return RequestResult.failure(ResultCode.INTERNAL_ERROR);
    }

    /**
     * 500 无法预料的异常
     */
    @ExceptionHandler(Throwable.class)
    public RequestResult handle(Throwable e) {
        RequestTrack requestTrack = RequestTrack.getCurrent();
        String insignia = requestTrack.getInsignia();
        log.error("统一异常处理被击穿！标识：" + insignia, e);
        return RequestResult.failure(ResultCode.INTERNAL_ERROR);
    }

}