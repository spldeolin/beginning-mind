package com.spldeolin.beginningmind.demo.valid;

import java.util.Random;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于演示ExceptionHandler效果
 *
 * @author Deolin 2019-09-06
 */
@RestController
public class ExceptionAdviceDemoController {

    /**
     * 使用GET请求这个handler时，
     * 会抛出HttpRequestMethodNotSupportedException
     */
    @PostMapping("/httpMethodError")
    String httpMethodError() {
        return "SUCCESS";
    }

    /**
     * 请求体 {"id":1, "name":"汉字"}
     * 并不指定Content-Type=application/json请求这个handler时，
     * 会抛出HttpMediaTypeNotSupportedException
     */
    @PostMapping("/contentTypeError")
    String contentTypeError(@RequestBody NestDto simpleDTO) {
        return "SUCCESS" + simpleDTO;
    }

    /**
     * 请求体 {"id":::1]
     * 会抛出HttpMessageNotReadableException
     */
    @PostMapping("/bodyNotReadable")
    String bodyNotReadable(@RequestBody NestDto simpleDTO) {
        return "SUCCESS" + simpleDTO;
    }

    /**
     * 以下请求体
     * { "id":1, "name":"汉字汉字汉字a" }
     * { "id":1, "name":"a", "dtos":[] }
     * { "id":1, "name":"a", "dtos":[ null, {} ] }
     * { "id":1, "name":"a", "dtos":[ { "age":-1, "salary":0.01 } ] }
     * 会抛出MethodArgumentNotValidException
     */
    @PostMapping("bodyInvalid")
    String bodyInvalid(@RequestBody @Valid NestDto simpleDTO) {
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

}
