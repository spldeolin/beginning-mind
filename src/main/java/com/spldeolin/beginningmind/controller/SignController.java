package com.spldeolin.beginningmind.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
import com.spldeolin.beginningmind.service.SecurityAccountService;
import com.spldeolin.beginningmind.util.RequestContextUtil;
import com.spldeolin.beginningmind.util.Signer;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("sign")
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
    @GetMapping("verify_code")
    public RequestResult verifyCode() {
        try (ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream()) {
            String createText = defaultKaptcha.createText();
            RequestContextUtil.session().setAttribute(VERIFY_CODE, createText);
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
    @PostMapping("sign_in")
    public RequestResult signIn(@RequestBody @Valid SignInput input) {
        // 验证码、重复登录校验
        String createText = (String) RequestContextUtil.session().getAttribute(VERIFY_CODE);
        if (!input.getVerifyCode().equals(createText)) {
            throw new ServiceException("验证码错误");
        }
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            throw new ServiceException("请勿重复登录");
        }
        // 登录
        try {
            subject.login(input.toToken());
        } catch (AuthenticationException e) {
            throw new ServiceException(e.getMessage());
        }
        // 登录成功后，为Spring Session管理的会话追加标识，用于定位当前会话
        RequestContextUtil.session().setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME,
                Signer.current().getSecurityAccount().getId().toString());
        return RequestResult.success();
    }

    /**
     * 登出
     */
    @DeleteMapping("sign_out")
    public RequestResult signOut() {
        SecurityUtils.getSubject().logout();
        return RequestResult.success();
    }

    /**
     * 指定用户是否登录中
     */
    @GetMapping("is_signing")
    public RequestResult isSign(@RequestParam("account_id") Long accountId) {
        return RequestResult.success(securityAccountService.isAccountSigning(accountId));
    }

    /**
     * 将指定用户踢下线
     */
    @DeleteMapping("kill")
    public RequestResult kill(@RequestParam("account_id") Long accountId) {
        securityAccountService.killSigner(accountId);
        return RequestResult.success();
    }

    @GetMapping("anon")
    public RequestResult anon() {
        return RequestResult.success("初心");
    }

}
