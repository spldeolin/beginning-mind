package com.spldeolin.beginningmind.controller;

import javax.validation.constraints.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.input.UserInput;
import com.spldeolin.beginningmind.properties.Properties;
import com.spldeolin.beginningmind.service.UserService;
import com.spldeolin.cadeau.library.dto.RequestResult;
import com.spldeolin.cadeau.library.exception.ServiceException;
import com.spldeolin.cadeau.library.util.RequestContextUtil;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("test")
@Log4j2
@Validated
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private Properties properties;

    @GetMapping("quqi")
    public RequestResult quqi() {
        return RequestResult.success(properties.getOneCookie());
    }

    @GetMapping("one")
    public RequestResult one(@RequestParam @Max(10) Integer one) {
        return RequestResult.success(one);
    }

    @GetMapping("java8time")
    public RequestResult java8time(@RequestParam Long id) {
        return RequestResult.success(userService.get(id).orElseThrow(() -> new ServiceException("不存在")));
    }

    @PutMapping("java8time2")
    public RequestResult java8time2(@RequestBody UserInput userInput) {
        return RequestResult.success(userInput);
    }

    @GetMapping("set_ses")
    public RequestResult setSes() {
        RequestContextUtil.session().setAttribute("cookie-in-session", "会话中的曲奇饼干");
        return RequestResult.success();
    }

    @GetMapping("get_ses")
    public RequestResult getSes() {
        return RequestResult.success(RequestContextUtil.session().getAttribute("cookie-in-session"));
    }

}
