package com.spldeolin.beginningmind.core.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.imageio.ImageIO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.core.CoreProperties;
import com.spldeolin.beginningmind.core.api.exception.ServiceException;
import com.spldeolin.beginningmind.core.constant.DirectoryName;
import com.spldeolin.beginningmind.core.input.SignInput;
import com.spldeolin.beginningmind.core.model.SecurityUser;
import com.spldeolin.beginningmind.core.util.Sessions;
import com.spldeolin.beginningmind.core.util.Signer;
import com.spldeolin.beginningmind.core.util.string.StringRandomUtils;
import com.spldeolin.beginningmind.core.vo.SignerProfileVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@Service
public class SignService {

    public static final String CAPTCHA_DIRECTORY = "captcha";

    private static final String CAPTCHA = "{CAPTCHA}";

    @Autowired
    private SecurityUserService securityAccountService;

    @Autowired
    private CoreProperties coreProperties;

    @SneakyThrows
    public String captcha() {
        String code = StringRandomUtils.generateNum(4);
        String location = coreProperties.getFile().getLocation();
        String mapping = coreProperties.getFile().getMapping();
        // session
        Sessions.set(CAPTCHA, code);
        // file
        String fileName = DigestUtils.md5Hex(String.valueOf(System.currentTimeMillis())) + ".jpg";
        File captchaFile = new File(location + DirectoryName.CAPTCHA_DIRECTORY + File.separator + fileName);
        int width = 150;
        int height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // 背景色
        g.setColor(new Color(82, 155, 240));
        g.fillRect(0, 0, width, height);
        // 字色
        g.setColor(Color.white);
        g.setFont(new Font("黑体", Font.BOLD, 36));
        for (int i = 0; i < code.length(); i++) {
            String oneChar = code.substring(i, i + 1);
            g.drawString(oneChar, 4 + 40 * i, 30);
        }
        try (FileOutputStream out = FileUtils.openOutputStream(captchaFile)) {
            ImageIO.write(image, "PNG", out);
        }
        // 映射
        return coreProperties.getAddress() + mapping + CAPTCHA_DIRECTORY + "/" + fileName;
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
                ChronoUnit.MINUTES.between(captchaDTO.getGeneratedAt(), LocalDateTime.now()) > 5) {
            throw new ServiceException("验证码超时");
        }
        String captcha = captchaDTO.getCaptcha();
        // DEBUG环境下，验证码可以随便填
        if (!coreProperties.isDebug()) {
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
        SecurityUser user = Signer.current().getSecurityUser();
        // 登录成功后，为Spring Session管理的会话追加标识，用于定位当前会话
        Sessions.set(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, user.getId().toString());
        // TODO 登录者拥有的权限存入profile
        // TODO 登录者拥有的菜单存入profile
        // profile
        return SignerProfileVO.builder().username(user.getUsername()).build();
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
        return securityAccountService.isAccountSigning(userId);
    }

    /**
     * 将指定用户踢下线
     */
    public void kill(Long userId) {
        securityAccountService.killSigner(userId);
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
