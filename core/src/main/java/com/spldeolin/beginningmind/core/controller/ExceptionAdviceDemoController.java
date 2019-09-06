package com.spldeolin.beginningmind.core.controller;

import java.util.List;
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
import lombok.Data;

/**
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
    String contentTypeError(@RequestBody SimpleDTO simpleDTO) {
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
    String ListJsonBodyInvalid(@RequestBody @Valid List<SimpleDTO> dtos) {
        return "SUCCESS" + dtos;
    }

    /**
     * 用以下的URL请求这个handler时，会抛出BindException
     * /multiParameterInvalid                       (absent)
     * /multiParameterInvalid?id=a&name=aa          (type error)
     * /multiParameterInvalid?id=1&name=aaaaaaaaa   (invalid)
     */
    @GetMapping("/multiParameterInvalid")
    String multiParameterInvalid(@Valid SimpleDTO simpleDTO) {
        return "SUCCESS" + simpleDTO;
    }

    /**
     * 请求体 {"id":1..........."name":"汉字"}
     * 会抛出HttpMessageNotReadableException
     */
    @PostMapping("/bodyNotReadable")
    String bodyNotReadable(@RequestBody SimpleDTO simpleDTO) {
        return "SUCCESS" + simpleDTO;
    }

    /**
     * 请求体 {"id":1, "name":"汉字汉字汉字a"}
     * 会抛出MethodArgumentNotValidException
     */
    @PostMapping("bodyInvalid")
    String bodyInvalid(@RequestBody @Valid SimpleDTO simpleDTO) {
        return "SUCCESS" + simpleDTO;
    }

    @Data
    static class SimpleDTO {

        @NotNull
        private Long id;

        @NotBlank
        @Size(max = 6)
        private String name;

    }

}
