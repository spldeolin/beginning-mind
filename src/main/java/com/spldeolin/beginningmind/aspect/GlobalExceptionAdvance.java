package com.spldeolin.beginningmind.aspect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.shiro.authz.AuthorizationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.aspect.dto.ControllerInfo;
import com.spldeolin.beginningmind.aspect.dto.Invalid;
import com.spldeolin.beginningmind.aspect.exception.ExtraInvalidException;
import com.spldeolin.beginningmind.aspect.exception.RequestNotFoundException;
import com.spldeolin.beginningmind.CoreProperties;
import com.spldeolin.beginningmind.constant.ResultCode;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.security.exception.UnsignedException;
import com.spldeolin.beginningmind.util.RequestContextUtils;
import lombok.extern.log4j.Log4j2;

/**
 * 控制层增强：统一异常处理
 *
 * @author Deolin
 * @see com.spldeolin.beginningmind.constant.ResultCode
 */
@Component
@RestControllerAdvice
@Log4j2
public class GlobalExceptionAdvance {

    @Autowired
    private ErrorAttributes errorAttributes;

    @Autowired
    private CoreProperties properties;

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
        ControllerInfo controllerInfo = RequestContextUtils.getControllerInfo();
        String[] paramNames = controllerInfo.getParameterNames();
        Object[] paramValues = controllerInfo.getParameterValues();
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
    public RequestResult handle(Throwable e, WebRequest webRequest) {
        ControllerInfo controllerInfo = RequestContextUtils.getControllerInfo();
        RequestResult requestResult;
        if (controllerInfo == null) {
            log.error("统一异常处理被击穿！", e);
            requestResult = RequestResult.failure(ResultCode.INTERNAL_ERROR);
        } else {
            String insignia = controllerInfo.getInsignia();
            log.error("统一异常处理被击穿！标识：" + insignia, e);
            requestResult = RequestResult.failure(ResultCode.INTERNAL_ERROR, "内部错误（" + insignia + "）");
        }
        // 堆栈轨迹
        if (properties.isDebug()) {
            requestResult.setData(getStacks(webRequest).get("trace"));
        }
        return requestResult;
    }

    private Map<String, Object> getStacks(WebRequest webRequest) {
        return errorAttributes.getErrorAttributes(webRequest, true);
    }

}