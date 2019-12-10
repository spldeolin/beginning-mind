package com.spldeolin.beginningmind.controller;

import java.util.List;
import java.util.Random;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.base.MoreObjects;
import com.spldeolin.beginningmind.exception.BizException;
import lombok.Data;

/**
 * 用于演示ExceptionHandler效果
 *
 * @author Deolin 2019-09-06
 */
@RestController
@Validated
public class ExceptionAdviceDemoController {

    /**
     * 使用POST请求这个handler时，
     * 会抛出HttpRequestMethodNotSupportedException
     */
    @GetMapping("/httpMethodError")
    String httpMethodError() {
        return "SUCCESS";
    }

    /**
     * 请求体 {"id":1, "name":"汉字"}
     * 并不指定Content-Type=application/json请求这个handler时，
     * 会抛出HttpMediaTypeNotSupportedException
     */
    @PostMapping("/contentTypeError")
    String contentTypeError(@RequestBody SimpleInputDTO simpleDTO) {
        return "SUCCESS" + simpleDTO;
    }

    /**
     * <pre>
     * 用URL /parameterAbsent?id=1请求这个handler时，
     * 会抛出MissingServletRequestParameterException
     *
     * 用以下的URL请求这个handler时，
     *  /parameterAbsent?id=1&name
     *  /parameterAbsent?id=1&name=
     * 不会抛出MissingServletRequestParameterException，但name确实会是null，
     * 所以自定义了额外的校验切面ControllerAspect#around，并抛出自定义异常ExtraInvalidException
     * </pre>
     */

    @GetMapping("/parameterAbsent")
    String parameterAbsent(@RequestParam(required = false) Long id, @RequestParam String name) {
        return "SUCCESS" + MoreObjects.firstNonNull(id, -1L) + name;
    }

    /**
     * 用URL /parameterTypeError?id=a&age=b请求这个handler时，
     * 会抛出MethodArgumentTypeMismatchException
     */
    @GetMapping("/parameterTypeError")
    String parameterTypeError(@RequestParam Long id, @RequestParam Integer age) {
        return "SUCCESS" + id + age;
    }

    /**
     * 用URL /parameterInvalid?name=aaabbbc&age=-1请求这个handler时，
     * 会抛出ConstraintViolationException
     */
    @GetMapping("/parameterInvalid")
    String parameterInvalid(@RequestParam @Size(max = 6) String name, @RequestParam @Min(0) Integer age) {
        return "SUCCESS" + name + age;
    }

    /**
     * 请求体 [ {"id":1, "name":"汉字汉字汉字a"}, {"id":null, "name":"bb"} ]
     * 会抛出ConstraintViolationException
     */
    @PostMapping("/ListJsonBodyInvalid")
    String ListJsonBodyInvalid(@RequestBody @Valid List<SimpleInputDTO> dtos) {
        return "SUCCESS" + dtos;
    }

    /**
     * 用以下的URL请求这个handler时，会抛出BindException
     * /multiParameterInvalid                       (absent)
     * /multiParameterInvalid?id=a&name=aa          (type error)
     * /multiParameterInvalid?id=1&name=aaaaaaaaa   (invalid)
     */
    @GetMapping("/multiParameterInvalid")
    String multiParameterInvalid(@Valid SimpleInputDTO simpleDTO) {
        return "SUCCESS" + simpleDTO;
    }

    /**
     * 请求体 {"id":1..........."name":"汉字"}
     * 会抛出HttpMessageNotReadableException
     */
    @PostMapping("/bodyNotReadable")
    String bodyNotReadable(@RequestBody SimpleInputDTO simpleDTO) {
        return "SUCCESS" + simpleDTO;
    }

    /**
     * 请求体 {"id":1, "name":"汉字汉字汉字a"}
     * 会抛出MethodArgumentNotValidException
     */
    @PostMapping("bodyInvalid")
    String bodyInvalid(@RequestBody @Valid SimpleInputDTO simpleDTO) {
        return "SUCCESS" + simpleDTO;
    }

    /**
     * 演示一个业务场景：获取学生的到校时间，如果尚未到达，抛出一个自定义的业务异常StudentNotComeYetException
     * 如果没有为这个异常编写专门的ExceptionHander，这个异常会因为继承关系而被BizExceptionHandler捕获
     */
    @GetMapping("/getStudentCameTime")
    Object getStudentCameTime() {

        if (new Random().nextBoolean()) {
            throw new StudentNotComeYetException();
        }

        return null;
    }

    /**
     * 一个用于参数绑定的DTO
     */
    @Data
    static class SimpleInputDTO {

        @NotNull
        private Long id;

        @NotBlank
        @Size(max = 6)
        private String name;

    }

    /**
     * 一个具体业务的业务异常类
     */
    static class StudentNotComeYetException extends BizException {

        private static final String DEFAULT_MESSAGE = "这是一个具体业务的业务异常示例类，代表「该学生了当前还未达到学校」的含义";

        public StudentNotComeYetException() {
            super(DEFAULT_MESSAGE);
        }

    }

}
