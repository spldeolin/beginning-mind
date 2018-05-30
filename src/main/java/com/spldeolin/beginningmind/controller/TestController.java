/*
 * Created by IntelliJ IDEA File Templates.
 */

package com.spldeolin.beginningmind.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.config.BeginningMindProperties;
import com.spldeolin.beginningmind.model.Goods;
import com.spldeolin.beginningmind.util.Signer;
import com.spldeolin.beginningmind.valid.annotation.Mobile;
import com.spldeolin.beginningmind.valid.annotation.Require;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * 测试
 * 仅dev环境可用
 *
 * @author Deolin 2018/05/26
 */
@Profile("dev")
@RestController
@RequestMapping("/test")
@Validated
@Log4j2
public class TestController {

    @GetMapping("/time")
    Object time() {
        return TimeOutput.builder().localDateTime(LocalDateTime.now()).date(new Date()).build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    private static class TimeOutput {

        private LocalDateTime localDateTime;

        private Date date;

    }

    @GetMapping("/set")
    Object setSes(HttpSession ses) {
        ses.setAttribute("one-cookie", "会话中的曲奇饼干");
        return "SUCCESS";
    }

    @GetMapping("/get")
    Object getSes(HttpSession ses) {
        log.info("asd");
        log.info("asd");
        log.info("asd");
        log.info("asd");
        return ses.getAttribute("one-cookie");
    }

    @PostMapping("require")
    Object testRequire(@Require(value = {"id", "name"}, message = "id或name为null") Goods goods) {
        return goods;
    }

    @GetMapping("mobile")
    Object testMobile(@RequestParam @Mobile String mobile) {
        return mobile;
    }

    @Autowired
    private BeginningMindProperties properties;

    @GetMapping("generateTxtFile")
    @SneakyThrows
    Object generateTxtFile(@RequestParam String content, @RequestParam String fileName) {
        String fileFullName = fileName + ".txt";
        String filePath = properties.getFile().getLocation() + fileFullName;
        FileUtils.write(new File(filePath), content, StandardCharsets.UTF_8);
        String relativeMapping = properties.getFile().getMapping() + fileFullName;
        return properties.getAddress() + relativeMapping;
    }

    @GetMapping("/e")
    void e() {
        log.info(Integer.valueOf("a"));
    }

    @GetMapping("email")
    Object email(@RequestParam @Email String email) {
        log.info(email);
        return null;
    }

    @GetMapping("signer")
    Object signer() {
        return Signer.current();
    }

}