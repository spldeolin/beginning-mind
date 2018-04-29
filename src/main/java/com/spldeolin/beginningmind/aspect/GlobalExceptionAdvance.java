package com.spldeolin.beginningmind.aspect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.aspect.dto.ControllerInfo;
import com.spldeolin.beginningmind.aspect.dto.Invalid;
import com.spldeolin.beginningmind.aspect.exception.ExtraInvalidException;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.dto.RequestResult;
import com.spldeolin.beginningmind.util.RequestContextUtil;
import com.spldeolin.beginningmind.util.StringCaseUtil;
import lombok.extern.log4j.Log4j2;

/**
 * 控制层增强：统一异常处理
 * <pre>
 *  400 API交互失败
 *  401 没有认证        （不在演示范围中）
 *  403 没有权限        （不在演示范围中）
 *  500 内部BUG
 *  200 成功
 * 1001 成功，但是存在业务异常（不在演示范围中）
 *                            （e.g.:请求的资源已被删除、禁用等）
 * </pre>
 *
 * @author Deolin
 */
@Component
@RestControllerAdvice
@Log4j2
public class GlobalExceptionAdvance {

    /**
     * 400 请求动词错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RequestResult handle(HttpRequestMethodNotSupportedException e) {
        return RequestResult.failure(ResultCode.BAD_REQEUST,
                "请求动词不受支持，当前为[" + e.getMethod() + "]，正确为" + Arrays.toString(e.getSupportedMethods()));
    }

    /**
     * 400 请求Content-Type错误
     * 往往是因为后端的@RequestBody和前端的application/json没有同时指定或同时不指定。
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public RequestResult handle(HttpMediaTypeNotSupportedException e) {
        return RequestResult.failure(ResultCode.BAD_REQEUST,
                "Content-Type错误，当前为[" + e.getContentType().toString().replace(";charset=UTF-8", "") +
                        "]，正确为[application/json]");
    }

    /**
     * 400 缺少RequestParam参数
     * <pre>
     *     但以下情况不会被捕获：
     *     /user?type
     *     /user?type=
     * </pre>
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RequestResult handle(MissingServletRequestParameterException e) {
        return RequestResult.failure(ResultCode.BAD_REQEUST,
                "缺少请求参数" + StringCaseUtil.camelToSnake(e.getParameterName()));
    }

    /**
     * 400 RequestParam参数类型错误
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RequestResult handle(MethodArgumentTypeMismatchException e) {
        return RequestResult.failure(ResultCode.BAD_REQEUST, e.getName() + "类型错误");
    }

    /**
     * 400 RequestParam参数没有通过注解校验
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public RequestResult handle(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
        ControllerInfo controllerInfo = RequestContextUtil.getControllerInfo();
        String[] paramNames = controllerInfo.getParameterNames();
        Object[] paramValues = controllerInfo.getParameterValues();
        List<Invalid> invalids = new ArrayList<>();
        for (ConstraintViolation<?> cv : cvs) {
            // 参数路径
            PathImpl pathImpl = (PathImpl) cv.getPropertyPath();
            // 参数下标
            int paramIndex = pathImpl.getLeafNode().getParameterIndex();
            invalids.add(Invalid.builder().name(StringCaseUtil.camelToSnake(paramNames[paramIndex])).value(
                    paramValues[paramIndex]).cause(cv.getMessage()).build());
        }
        return RequestResult.failure(ResultCode.BAD_REQEUST, "数据校验未通过").setData(invalids);
    }

    /**
     * 400 请求Body格式错误
     * <pre>
     *     以下情况时，被捕获：
     *     alkdjfaldfjlalkajkdklf
     *     (空)
     *     {"user_age"="word"}
     * </pre>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RequestResult httpMessageNotReadable() {
        return RequestResult.failure(ResultCode.BAD_REQEUST, "请求Body不可读。可能是JSON格式错误，或JSON不存在，或类型错误");
    }

    /**
     * 400 请求Body内字段没有通过注解校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RequestResult handle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<Invalid> invalids = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            invalids.add(Invalid.builder().name(StringCaseUtil.camelToSnake(fieldError.getField())).value(
                    fieldError.getRejectedValue()).cause(fieldError.getDefaultMessage()).build());
        }
        return RequestResult.failure(ResultCode.BAD_REQEUST, "数据校验未通过").setData(invalids);
    }

    /**
     * 400 RequestParam参数或请求Body内字段没有通过额外的检验
     */
    @ExceptionHandler(ExtraInvalidException.class)
    public RequestResult handle(ExtraInvalidException e) {
        return RequestResult.failure(ResultCode.BAD_REQEUST, "数据校验未通过").setData(e.getInvalids());
    }

    /**
     * 1001 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public RequestResult handle(ServiceException e) {
        return RequestResult.failure(ResultCode.SERVICE_ERROR, e.getMessage());
    }

    /**
     * 500 内部BUG
     */
    @ExceptionHandler(Throwable.class)
    public RequestResult handle(Throwable e) {
        String insignia = RequestContextUtil.getControllerInfo().getInsignia();
        log.error("统一异常处理被击穿！标识：" + insignia, e);
        return RequestResult.failure(ResultCode.INTERNAL_ERROR, "内部错误（" + insignia + "）");
    }

}