package com.spldeolin.beginningmind.aspect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
import org.springframework.web.servlet.NoHandlerFoundException;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.aspect.dto.Invalid;
import com.spldeolin.beginningmind.aspect.dto.RequestResult;
import com.spldeolin.beginningmind.aspect.exception.ExtraInvalidException;
import com.spldeolin.beginningmind.filter.dto.RequestTrackDTO;
import com.spldeolin.beginningmind.util.WebContext;
import lombok.extern.log4j.Log4j2;

/**
 * 控制层Advice切面：Http请求相关异常处理
 *
 * @author Deolin
 * @see ResultCode
 */
@RestControllerAdvice
@Log4j2
public class RequestExceptionAdvice {

    /**
     * 404 Not Found
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public RequestResult handle(NoHandlerFoundException e) {
        log.warn("找不到handler，[{} {}]", e.getHttpMethod(), e.getRequestURL());
        return RequestResult.failure(ResultCode.NOT_FOUND);
    }

    /**
     * 400 请求动词错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RequestResult handle(HttpRequestMethodNotSupportedException e) {
        log.warn("请求动词不受支持，当前为[{}] 正确为[{}]", e.getMessage(), Arrays.toString(e.getSupportedMethods()));
        return RequestResult.failure(ResultCode.BAD_REQEUST);
    }

    /**
     * 400 请求Content-Type错误
     * <pre>
     * 往往是因为后端的@RequestBody和前端的application/json没有同时指定或同时不指定导致的
     * </pre>
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public RequestResult handle(HttpMediaTypeNotSupportedException e) {
        MediaType mediaType = e.getContentType();
        String message = "Content-Type错误，";
        if (mediaType != null) {
            message += "当前为[" + e.getContentType().toString().replace(";charset=UTF-8", "") + "]，";
        }
        message += "正确为[application/json]。";
        log.warn(message);
        return RequestResult.failure(ResultCode.BAD_REQEUST);
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
        log.warn("缺少请求参数[{}]", e.getParameterName());
        return RequestResult.failure(ResultCode.BAD_REQEUST);
    }

    /**
     * 400 RequestParam参数类型错误
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RequestResult handle(MethodArgumentTypeMismatchException e) {
        log.warn("类型错误[{}]", e.getName());
        return RequestResult.failure(ResultCode.BAD_REQEUST);
    }

    /**
     * 400 @RequestParam参数没有通过注解校验（控制器声明@Validated时）
     * 或者 @RequestBody List泛型 没有通过注解校验
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public RequestResult handle(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
        RequestTrackDTO track = WebContext.getRequestTrack();
        if (track == null) {
            throw new RuntimeException("获取失败，当前线程并不是Web请求线程");
        }

        List<Invalid> invalids = new ArrayList<>();
        for (ConstraintViolation<?> cv : cvs) {
            // 参数路径
            PathImpl pathImpl = (PathImpl) cv.getPropertyPath();
            // 参数下标
            NodeImpl dto = pathImpl.getLeafNode();
            Invalid invalid = new Invalid(dto.getName(), cv.getInvalidValue(), cv.getMessage());
            invalids.add(invalid);
        }
        log.warn(invalids);
        return RequestResult.failure(ResultCode.BAD_REQEUST);
    }

    /**
     * 400 RequestParam参数没有通过注解校验（multi-param且声明@Valid时）
     */
    @ExceptionHandler(BindException.class)
    public RequestResult handle(BindException e) {
        List<Invalid> invalids = buildInvalids(e.getBindingResult());
        log.warn(invalids);
        return RequestResult.failure(ResultCode.BAD_REQEUST);
    }

    /**
     * 400 请求Body格式错误
     * <pre>
     * 以下情况时，会被捕获：
     * alkdjfaldfjlalkajkdklf               可能是因为Body不是合法的JSON格式
     * (空)                                 JSON不存在
     * {"userAge"="notNumberValue"}         JSON中字段类型错误
     * </pre>
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RequestResult httpMessageNotReadable() {
        log.warn("请求Body不可读");
        return RequestResult.failure(ResultCode.BAD_REQEUST);
    }

    /**
     * 400 请求Body内字段没有通过注解校验（形参声明@Valid时）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RequestResult handle(MethodArgumentNotValidException e) {
        List<Invalid> invalids = buildInvalids(e.getBindingResult());
        log.warn(invalids);
        return RequestResult.failure(ResultCode.BAD_REQEUST);
    }

    /**
     * 400 成功绑定并通过校验的请求方法参数，没有通过切面的额外校验（如?temp&foo=这样的情况）
     */
    @ExceptionHandler(ExtraInvalidException.class)
    public RequestResult handle(ExtraInvalidException e) {
        log.warn(e.getInvalids());
        return RequestResult.failure(ResultCode.BAD_REQEUST);
    }

    private List<Invalid> buildInvalids(BindingResult bindingResult) {
        List<Invalid> invalids = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            Invalid invalid = new Invalid(fieldError.getField(), fieldError.getRejectedValue(),
                    fieldError.getDefaultMessage());
            invalids.add(invalid);
        }
        return invalids;
    }

}