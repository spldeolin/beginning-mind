package com.spldeolin.beginningmind.controller.manager;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.spldeolin.beginningmind.api.exception.ServiceException;
import com.spldeolin.beginningmind.config.BeginningMindProperties;
import com.spldeolin.beginningmind.controller.dto.RequestResult;
import com.spldeolin.beginningmind.input.SignInput;
import com.spldeolin.beginningmind.model.SecurityAccount;
import com.spldeolin.beginningmind.security.GifCaptcha;
import com.spldeolin.beginningmind.service.SecurityAccountService;
import com.spldeolin.beginningmind.util.Sessions;
import com.spldeolin.beginningmind.util.Signer;
import com.spldeolin.beginningmind.vo.SignerProfileVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class SignManager {

    private static final String CAPTCHA = "{CAPTCHA}";

    @Autowired
    private SecurityAccountService securityAccountService;

    @Autowired
    private BeginningMindProperties beginningMindProperties;

    public String captcha() {
        // 生成文件名
        String location = beginningMindProperties.getFile().getLocation();
        String fullFileName = System.currentTimeMillis() + ".gif";
        // 生成文件
        File captchaFile = new File(location + File.separator + fullFileName);
        try (OutputStream os = FileUtils.openOutputStream(captchaFile)) {
            GifCaptcha gifCaptcha = new GifCaptcha();
            gifCaptcha.out(os);
            Sessions.set(CAPTCHA, CaptchaDTO.builder().captcha(gifCaptcha.getCode()).generatedAt(
                    LocalDateTime.now()).build());
            // 映射URL
            String fullMapping = beginningMindProperties.getAddress() + beginningMindProperties.getFile().getMapping()
                    + fullFileName;
            return fullMapping;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 登录
     */
    public SignerProfileVO signIn(SignInput input) {
        // 重复登录、验证码校验
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            throw new ServiceException("请勿重复登录");
        }
        CaptchaDTO captchaDTO = Sessions.get(CAPTCHA);
        Sessions.remove(CAPTCHA);
        if (captchaDTO == null ||
                ChronoUnit.MINUTES.between(LocalDateTime.now(), captchaDTO.getGeneratedAt()) > 5) {
            throw new ServiceException("验证码超时");
        }
        String captcha = captchaDTO.getCaptcha();
        // DEBUG环境下，验证码可以随便填
        if (!beginningMindProperties.isDebug()) {
            if (!captcha.equalsIgnoreCase(input.getCaptcha())) {
                throw new ServiceException("验证码错误");
            }
        }
        // 登录
        try {
            subject.login(input.toToken());
        } catch (AuthenticationException e) {
            throw new ServiceException(e.getMessage());
        }
        SecurityAccount account = Signer.current().getSecurityAccount();
        // 登录成功后，为Spring Session管理的会话追加标识，用于定位当前会话
        Sessions.set(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, account.getId().toString());
        // profile
        return SignerProfileVO.builder().username(account.getUsername()).build();
    }

    /**
     * 登出
     */
    public void signOut() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 指定用户是否登录中
     */
    @GetMapping("is_signing")
    public RequestResult isSign(@RequestParam Long accountId) {
        return RequestResult.success(securityAccountService.isAccountSigning(accountId));
    }

    /**
     * 将指定用户踢下线
     */
    @DeleteMapping("kill")
    public RequestResult kill(@RequestParam Long accountId) {
        securityAccountService.killSigner(accountId);
        return RequestResult.success();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class CaptchaDTO implements Serializable {

        private String captcha;

        private LocalDateTime generatedAt;

        private static final long serialVersionUID = 1L;

    }

}
