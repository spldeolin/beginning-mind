package com.spldeolin.beginningmind.core.service.impl;

import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Service;
import com.google.code.kaptcha.Producer;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import com.spldeolin.beginningmind.core.dto.SignerProfileDTO;
import com.spldeolin.beginningmind.core.input.SignInput;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.security.util.Signer;
import com.spldeolin.beginningmind.core.service.SignService;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.Sessions;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;
import lombok.SneakyThrows;

/**
 * @author Deolin
 */
@Service
public class SignServiceImpl implements SignService {

    private static final String CAPTCHA_SESSION_KEY = "captcha";

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @SneakyThrows
    public String captcha() {
        String code = StringRandomUtils.generateLegibleEnNum(4);

        // session
        Sessions.set(CAPTCHA_SESSION_KEY, code);

        // base64
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(kaptchaProducer.createImage(code), "jpg", os);
            return "data:image/jpg;base64," + Base64.encodeBase64String(os.toByteArray());
        }
    }

    /**
     * 登录
     */
    public SignerProfileDTO signIn(SignInput input) {
        // 重复登录、验证码校验
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            throw new ServiceException("请勿重复登录");
        }
        String captcha = Sessions.get(CAPTCHA_SESSION_KEY);
        Sessions.remove(CAPTCHA_SESSION_KEY);
        if (captcha == null) {
            throw new ServiceException("验证码超时");
        }
        if (!captcha.equalsIgnoreCase(input.getCaptcha())) {
            throw new ServiceException("验证码错误");
        }
        // 登录
        try {
            subject.login(input.toToken());
        } catch (AuthenticationException e) {
            throw new ServiceException(e.getMessage());
        }
        User user = Signer.current().getUser();
        // 登录成功后，为Spring Session管理的会话追加标识，用于定位当前会话
        Sessions.set(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, user.getId().toString());
        // TODO 登录者拥有的权限存入profile
        // TODO 登录者拥有的菜单存入profile
        // profile
        return SignerProfileDTO.builder().userName(user.getName()).build();
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
    public Boolean isSign(Long userId) {
        return userService.isAccountSigning(userId);
    }

    /**
     * 将指定用户踢下线
     */
    public void kill(Long userId) {
        userService.killSigner(userId);
    }

}
