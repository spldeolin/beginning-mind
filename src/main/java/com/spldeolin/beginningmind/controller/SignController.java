package com.spldeolin.beginningmind.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.config.BeginningMindProperties;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.input.SignInput;
import com.spldeolin.beginningmind.model.SecurityAccount;
import com.spldeolin.beginningmind.service.SecurityAccountService;
import com.spldeolin.beginningmind.util.RequestContextUtils;
import com.spldeolin.beginningmind.util.Signer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 登录、登出、踢出相关控制
 *
 * @author Deolin
 */
@RestController
@RequestMapping("/sign")
@Log4j2
@Validated
public class SignController {

    private static final String VERIFY_CODE = "{VERIFY_CODE}";

    @Autowired
    private SecurityAccountService securityAccountService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private BeginningMindProperties beginningMindProperties;

    /**
     * 获取验证码
     */
    @GetMapping("/verify_code")
    public RequestResult verifyCode() {
        try (ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream()) {
            String createText = defaultKaptcha.createText();
            RequestContextUtils.session().setAttribute(VERIFY_CODE,
                    VerifyCodeDTO.builder().verifyCode(createText).generatedAt(LocalDateTime.now()).build());
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
            String location = beginningMindProperties.getFile().getLocation();
            String fullFileName = System.currentTimeMillis() + ".jpg";
            FileUtils.writeByteArrayToFile(new File(location + File.separator + fullFileName),
                    jpegOutputStream.toByteArray());
            String fullMapping = beginningMindProperties.getAddress() + beginningMindProperties.getFile().getMapping();
            return RequestResult.success(fullMapping + "/" + fullFileName);
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 登录
     */
    @PostMapping("/in")
    public RequestResult signIn(@RequestBody @Valid SignInput input) {
        HttpSession session = RequestContextUtils.session();
        // 重复登录、验证码校验
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            throw new ServiceException("请勿重复登录");
        }
        VerifyCodeDTO verifyCodeDTO = (VerifyCodeDTO) session.getAttribute(VERIFY_CODE);
        session.removeAttribute(VERIFY_CODE);
        if (verifyCodeDTO == null ||
                ChronoUnit.MINUTES.between(LocalDateTime.now(), verifyCodeDTO.getGeneratedAt()) > 5) {
            throw new ServiceException("验证码超时");
        }
        String verifyCode = verifyCodeDTO.getVerifyCode();
        if (!verifyCode.equals(input.getVerifyCode())) {
            throw new ServiceException("验证码错误");
        }
        // 登录
        try {
            subject.login(input.toToken());
        } catch (AuthenticationException e) {
            throw new ServiceException(e.getMessage());
        }
        SecurityAccount account = Signer.current().getSecurityAccount();
        // 登录成功后，为Spring Session管理的会话追加标识，用于定位当前会话
        RequestContextUtils.session().setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME,
                account.getId().toString());
        Signer.mdc();
        return RequestResult.success();
    }

    /**
     * 登出
     */
    @DeleteMapping("/out")
    public RequestResult signOut() {
        SecurityUtils.getSubject().logout();
        // 清除MDC
        Signer.removeMDC();
        return RequestResult.success();
    }

    /**
     * 指定用户是否登录中
     */
    @GetMapping("/is_signing")
    public RequestResult isSign(@RequestParam("account_id") Long accountId) {
        return RequestResult.success(securityAccountService.isAccountSigning(accountId));
    }

    /**
     * 将指定用户踢下线
     */
    @DeleteMapping("/kill")
    public RequestResult kill(@RequestParam("account_id") Long accountId) {
        securityAccountService.killSigner(accountId);
        return RequestResult.success();
    }

    @GetMapping("/anon")
    public RequestResult anon() {
        return RequestResult.success("初心");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class VerifyCodeDTO implements Serializable {

        private String verifyCode;

        private LocalDateTime generatedAt;

        private static final long serialVersionUID = 1L;

    }

}
