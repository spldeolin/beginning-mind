/*
 * Created by IntelliJ IDEA File Templates.
 */

package com.spldeolin.beginningmind.core.controller2;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.security.dto.CurrentSigner;
import com.spldeolin.beginningmind.core.util.Signer;
import com.spldeolin.beginningmind.core.valid.annotation.Mobile;
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
    TimeOutput time() {
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
    String setSes(HttpSession ses) {
        ses.setAttribute("one-cookie", "会话中的曲奇饼干");
        return "SUCCESS";
    }

    @GetMapping("/get")
    String getSes(HttpSession ses) {
        log.info("asd");
        log.info("asd");
        log.info("asd");
        log.info("asd");
        return (String) ses.getAttribute("one-cookie");
    }

    @GetMapping("mobile")
    String testMobile(@RequestParam @Mobile String mobile) {
        return mobile;
    }

    @Autowired
    private CoreProperties properties;

    @GetMapping("generateTxtFile")
    @SneakyThrows
    String generateTxtFile(@RequestParam String content, @RequestParam String fileName) {
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
    void email(@RequestParam @Email String email) {
        log.info(email);
    }

    @GetMapping("signer")
    CurrentSigner signer() {
        return Signer.current();
    }

    @PostMapping("requestParamJavaBean")
    String requestParamJavaBean(JavaBean query, @RequestBody JavaBean body) {
        return query.toString() + body;
    }

    @Data
    private static class JavaBean {

        private String a;

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @JsonFormat(pattern = "yyyy:MM:dd'T'HH-mm-ss")
        private LocalDateTime b;

    }

    @GetMapping("multiRequestParamJavaBean")
    String multiRequestParamJavaBean(ConditionParam conditionParam, PageParam pageParam) {
        return conditionParam.toString() + pageParam;
    }

    @Data
    private static class ConditionParam {

        private String a;
    }

    @Data
    private static class PageParam {

        private Integer pageNo;

        private Integer pageSize;
    }

}