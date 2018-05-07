package com.spldeolin.beginningmind.controller;

import java.time.LocalDateTime;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/test")
@Log4j2
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

}

