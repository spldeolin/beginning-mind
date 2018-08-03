package com.spldeolin.beginningmind.core.aspect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.shiro.authz.AuthorizationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import com.spldeolin.beginningmind.core.aspect.dto.Invalid;
import com.spldeolin.beginningmind.core.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.core.aspect.dto.RequestTrack;
import com.spldeolin.beginningmind.core.aspect.exception.ExtraInvalidException;
import com.spldeolin.beginningmind.core.aspect.exception.RequestNotFoundException;
import com.spldeolin.beginningmind.core.constant.ResultCode;
import com.spldeolin.beginningmind.core.security.exception.UnsignedException;
import com.spldeolin.beginningmind.core.util.RequestContextUtils;
import lombok.extern.log4j.Log4j2;

/**
 * 控制层增强：统一异常处理
 *
 * @author Deolin
 * @see com.spldeolin.beginningmind.core.constant.ResultCode
 */
@Component
@RestControllerAdvice
@Log4j2
public class GlobalExceptionAdvance {

    @Autowired
    private CoreProperties coreProperties;

    /**
     * 400 请求动词错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RequestResult handle(HttpRequestMethodNotSupportedException e) {
        return RequestResult.failure(ResultCode.BAD_REQEUST,
                "请求动词不受支持，当前为[" + e.getMethod() + "]，正确为" + Arrays.toString(e.getSupportedMethods()));
    }

    /**
     * 400 请求Content-Type错误 往往是因为后端的@RequestBody和前端的application/json没有同时指定或同时不指定。
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
     * 但以下情况不会被捕获：
     * /user?type
     * /user?type=
     * </pre>
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RequestResult handle(MissingServletRequestParameterException e) {
        return RequestResult.failure(ResultCode.BAD_REQEUST, "缺少请求参数" + e.getParameterName());
    }

    /**
     * 400 RequestParam参数类型错误
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RequestResult handle(MethodArgumentTypeMismatchException e) {
        return RequestResult.failure(ResultCode.BAD_REQEUST, e.getName() + "类型错误");
    }

    /**
     * 400 RequestParam参数没有通过注解校验（控制器声明@Validated时）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public RequestResult handle(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
        RequestTrack requestTrack = RequestContextUtils.getRequestTrack();
        String[] paramNames = requestTrack.getParameterNames();
        Object[] paramValues = requestTrack.getParameterValues();
        List<Invalid> invalids = new ArrayList<>();
        for (ConstraintViolation<?> cv : cvs) {
            // 参数路径
            PathImpl pathImpl = (PathImpl) cv.getPropertyPath();
            // 参数下标
            int paramIndex = pathImpl.getLeafNode().getParameterIndex();
            invalids.add(Invalid.builder().name(paramNames[paramIndex]).value(paramValues[paramIndex]).cause(
                    cv.getMessage()).build());
        }
        return RequestResult.failure(ResultCode.BAD_REQEUST, "数据校验未通过").setData(invalids);
    }

    /**
     * 400 RequestParam参数没有通过注解校验（multi-param且声明@Valid时）
     */
    @ExceptionHandler(BindException.class)
    public RequestResult handle(BindException e) {
        List<Invalid> invalids = new ArrayList<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            invalids.add(Invalid.builder().name(fieldError.getField()).value(fieldError.getRejectedValue())
                    .cause(fieldError.getDefaultMessage()).build());
        }
        return RequestResult.failure(ResultCode.BAD_REQEUST, "数据校验未通过").setData(invalids);
    }

    /**
     * 400 请求Body格式错误
     * <pre>
     * 以下情况时，会被捕获：
     * alkdjfaldfjlalkajkdklf
     * (空)
     * {"userAge"="notNumberValue"}
     * </pre>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RequestResult httpMessageNotReadable() {
        return RequestResult.failure(ResultCode.BAD_REQEUST, "请求Body不可读。可能是JSON格式错误，或JSON不存在，或类型错误");
    }

    /**
     * 400 请求Body内字段没有通过注解校验（形参声明@Valid时）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RequestResult handle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<Invalid> invalids = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            invalids.add(Invalid.builder().name(fieldError.getField()).value(fieldError.getRejectedValue()).cause(
                    fieldError.getDefaultMessage()).build());
        }
        return RequestResult.failure(ResultCode.BAD_REQEUST, "数据校验未通过").setData(invalids);
    }

    /**
     * 400 成功绑定并通过校验的请求方法参数，没有通过切面的额外校验（如?temp&foo=这样的情况）
     */
    @ExceptionHandler(ExtraInvalidException.class)
    public RequestResult handle(ExtraInvalidException e) {
        return RequestResult.failure(ResultCode.BAD_REQEUST, "数据校验未通过").setData(e.getInvalids());
    }

    /**
     * 401 未登录
     */
    @ExceptionHandler(UnsignedException.class)
    public RequestResult handleUnsignException(UnsignedException e) {
        return RequestResult.failure(ResultCode.UNSIGNED, e.getMessage());
    }

    /**
     * 403 没权限或是请求actuator时提供的token不正确
     */
    @ExceptionHandler(AuthorizationException.class)
    public RequestResult handleAuthorizationException() {
        return RequestResult.failure(ResultCode.FORBIDDEN);
    }

    /**
     * 404 找不到请求
     */
    @ExceptionHandler(RequestNotFoundException.class)
    public RequestResult handleRequestNotFoundException() {
        return RequestResult.failure(ResultCode.NOT_FOUND);
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
     * <pre>
     * 控制层上方发生的BUG（如过滤器中的BUG），无法返回标识符。因为没有进入切面
     * 控制层下方发生的BUG（如果业务层、持久层的BUG），能够返回标识符
     * </pre>
     */
    @ExceptionHandler(Throwable.class)
    public RequestResult handle(Throwable e) {
        RequestTrack track = RequestContextUtils.getRequestTrack();
        RequestResult requestResult;
        if (track == null) {
            log.error("统一异常处理被击穿！", e);
            requestResult = RequestResult.failure(ResultCode.INTERNAL_ERROR);
        } else {
            String insignia = track.getInsignia();
            log.error("统一异常处理被击穿！标识：" + insignia, e);
            requestResult = RequestResult.failure(ResultCode.INTERNAL_ERROR, "内部错误（" + insignia + "）");
        }
        // 详细错误信息
        if (coreProperties.isDebug()) {
            String msg = e.getMessage();
            if (msg == null) {
                msg = e.getClass().getSimpleName();
            }
            if (msg.length() > 200) {
                msg = msg.substring(0, 200) + " ...";
            }
            requestResult.setMessage(requestResult.getMessage() + "，详细信息（" + msg + "）");
        }
        return requestResult;
    }

}