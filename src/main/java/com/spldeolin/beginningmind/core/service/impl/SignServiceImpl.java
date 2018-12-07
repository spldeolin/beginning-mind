package com.spldeolin.beginningmind.core.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.google.code.kaptcha.Producer;
import com.spldeolin.beginningmind.core.api.exception.BizException;
import com.spldeolin.beginningmind.core.dto.SignerProfileVO;
import com.spldeolin.beginningmind.core.input.SignInput;
import com.spldeolin.beginningmind.core.model.Permission;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.security.dto.CurrentSignerDTO;
import com.spldeolin.beginningmind.core.security.exception.PasswordIncorretException;
import com.spldeolin.beginningmind.core.security.exception.UserNotExistException;
import com.spldeolin.beginningmind.core.security.util.Signer;
import com.spldeolin.beginningmind.core.service.PermissionService;
import com.spldeolin.beginningmind.core.service.SignService;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.Sessions;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin
 */
@Service
@Log4j2
public class SignServiceImpl implements SignService {

    private static final String CAPTCHA_SESSION_KEY = "captcha";

    public static final String SIGNER_SESSION_KEY = "signer";

    public static final String SIGN_STATUS_BY_USER_ID = "signStatusByUserId:";

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String captcha() {
        String captcha = StringRandomUtils.generateLegibleEnNum(4);
        // session
        Sessions.set(CAPTCHA_SESSION_KEY, captcha, 5 * 60);
        // base64
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(kaptchaProducer.createImage(captcha), "jpg", os);
            return "data:image/jpg;base64," + Base64.encodeBase64String(os.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 登录
     */
    @Override
    public SignerProfileVO signIn(SignInput input) {
        // 获取用户，同时进行验证码、重复登录、用户名密码校验
        User user = signCheck(input);

        // 获取权限一览
        List<Permission> permissions = permissionService.listGrantedPermission(user.getId());

        // 获取会话ID
        String sessionId = Sessions.session().getId();

        // 用户信息存入Session
        CurrentSignerDTO currentSignerDTO = CurrentSignerDTO.builder()
                .user(user).permissions(permissions).sessionId(sessionId).signedAt(LocalDateTime.now())
                .build();
        Sessions.set(SIGNER_SESSION_KEY, currentSignerDTO, 30 * 60);

        // 登录状态（用户信息+会话ID）存入Redis
        redisTemplate.opsForHash().put(SIGN_STATUS_BY_USER_ID + user.getId(), sessionId, currentSignerDTO);

        // profile
        return SignerProfileVO.builder().userName(user.getName())
                .permissionIds(permissions.stream().map(Permission::getId).collect(Collectors.toList())).build();
    }

    /**
     * 登出
     */
    @Override
    public void signOut() {
        Long userId = Signer.userId();
        String sessionId = Sessions.session().getId();
        Sessions.remove(SIGNER_SESSION_KEY);
        redisTemplate.opsForHash().delete(SIGN_STATUS_BY_USER_ID + userId, sessionId);
    }

    /**
     * 指定用户是否登录中
     */
    @Override
    public Boolean isSigning(Long userId) {
        return redisTemplate.opsForHash().entries(SIGN_STATUS_BY_USER_ID + userId).size() > 0;
    }

    /**
     * 将指定用户踢下线
     */
    @Override
    public void kill(Long userId) {
        redisTemplate.delete(SIGN_STATUS_BY_USER_ID + userId);
    }

    private User signCheck(SignInput input) {
        // 验证码校验
        String captcha = Sessions.get(CAPTCHA_SESSION_KEY);
        Sessions.remove(CAPTCHA_SESSION_KEY);
        if (captcha == null) {
            throw new BizException("验证码超时");
        }
        if (!captcha.equalsIgnoreCase(input.getCaptcha())) {
            throw new BizException("验证码错误");
        }
        // 重复登录校验
        if (Signer.isSigning()) {
            throw new BizException("已登录，请勿重复登录");
        }
        // 用户名密码校验
        try {
            User user = tryGetUser(input.getPrincipal());
            checkPassword(user, input.getPassword());

            return user;
        } catch (UserNotExistException | PasswordIncorretException e) {
            throw new BizException("用户不存在或是密码错误");
        }
    }

    private User tryGetUser(String principal) throws UserNotExistException {
        Optional<User> userOpt = userService.searchOneByPrincipal(principal);
        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            log.info("用户不存在 {}", principal);
            throw new UserNotExistException();
        }
    }

    private void checkPassword(User user, String inputPassword) throws PasswordIncorretException {
        String salt = user.getSalt();
        String inputPasswordEx = DigestUtils.sha512Hex(inputPassword);
        if (!DigestUtils.sha512Hex(inputPasswordEx + salt).equals(user.getPassword())) {
            log.info("用户密码错误 {} {}", user.getId(), inputPassword);
            throw new PasswordIncorretException();
        }
    }

}
