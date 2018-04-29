package com.spldeolin.beginningmind.controller;

import java.time.LocalDateTime;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.dto.RequestResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@RestController
@RequestMapping("test")
public class TestController {

    @Secured("time")
    @GetMapping("time")
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

    @GetMapping("set")
    public String setSes(HttpSession ses) {
        ses.setAttribute("one-cookie", "会话中的曲奇饼干");
        return "SUCCESS";
    }

    @GetMapping("get")
    public String getSes(HttpSession ses) {
        return (String) ses.getAttribute("one-cookie");
    }

}

