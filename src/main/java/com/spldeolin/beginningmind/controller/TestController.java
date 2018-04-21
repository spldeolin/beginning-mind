package com.spldeolin.beginningmind.controller;

import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.cadeau.library.dto.RequestResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@RestController
@RequestMapping("test")
public class TestController {

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

}

