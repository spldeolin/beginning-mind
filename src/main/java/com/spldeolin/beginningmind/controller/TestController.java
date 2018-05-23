package com.spldeolin.beginningmind.controller;

import java.time.LocalDateTime;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.model.Goods;
import com.spldeolin.beginningmind.valid.annotation.Mobile;
import com.spldeolin.beginningmind.valid.annotation.Require;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/test")
@Log4j2
@Validated
public class TestController {

    @GetMapping("/time")
    public RequestResult time() {
        return RequestResult.success(TimeOutput.builder().localDateTime(LocalDateTime.now()).date(new Date()).build());
    }

    @Data
    @Builder
    @AllArgsConstructor
    private static class TimeOutput {

        private LocalDateTime localDateTime;

        private Date date;

    }

    @RequiresPermissions("/test/set")
    @GetMapping("/set")
    public String setSes(HttpSession ses) {
        ses.setAttribute("one-cookie", "会话中的曲奇饼干");
        return "SUCCESS";
    }

    @RequiresPermissions("/test/get")
    @GetMapping("/get")
    public String getSes(HttpSession ses) {
        log.info("asd");
        log.info("asd");
        log.info("asd");
        log.info("asd");
        return (String) ses.getAttribute("one-cookie");
    }

    @PostMapping("require")
    public RequestResult testRequire(@Require(value = {"id", "name"}, message = "id或name为null") Goods goods) {
        return RequestResult.success(goods);
    }

    public RequestResult testMobile(@RequestParam @Mobile String mobile) {
        return RequestResult.success(mobile);
    }

}

