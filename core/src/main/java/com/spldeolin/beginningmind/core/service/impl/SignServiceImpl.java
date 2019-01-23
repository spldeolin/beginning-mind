package com.spldeolin.beginningmind.core.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.google.code.kaptcha.Producer;
import com.spldeolin.beginningmind.core.api.BizException;
import com.spldeolin.beginningmind.core.dto.CaptchaVO;
import com.spldeolin.beginningmind.core.dto.SignerProfileVO;
import com.spldeolin.beginningmind.core.entity.PermissionEntity;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import com.spldeolin.beginningmind.core.input.SignInput;
import com.spldeolin.beginningmind.core.security.dto.CurrentSignerDTO;
import com.spldeolin.beginningmind.core.security.exception.PasswordIncorretException;
import com.spldeolin.beginningmind.core.security.exception.UserNotExistException;
import com.spldeolin.beginningmind.core.security.util.SignContext;
import com.spldeolin.beginningmind.core.service.PermissionService;
import com.spldeolin.beginningmind.core.service.SignService;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.Sessions;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;
import com.spldeolin.beginningmind.core.util.WebContext;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin
 */
@Service
@Log4j2
public class SignServiceImpl implements SignService {

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
    public CaptchaVO captcha() {
        String captcha = StringRandomUtils.generateLegibleEnNum(4);

        // cache
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, captcha, 5, TimeUnit.MINUTES);

        // base64
        String base64;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(kaptchaProducer.createImage(captcha), "png", os);
            base64 = "data:image/jpg;base64," + Base64.encodeBase64String(os.toByteArray());
        } catch (IOException e) {
            log.error("验证码生成失败", e);
            throw new BizException("验证码生成失败");
        }

        return new CaptchaVO(base64, token);
    }

    /**
     * 登录
     */
    @Override
    public SignerProfileVO signIn(SignInput input) {
        // 获取用户，同时进行验证码、重复登录、用户名密码校验
        UserEntity user = signCheck(input);

        // 获取权限一览
        List<PermissionEntity> permissions = permissionService.listGrantedPermission(user.getId());
        List<Long> permissionIds = permissions.stream().map(PermissionEntity::getId).collect(Collectors.toList());

        // 获取会话ID
        String sessionId = WebContext.getSession().getId();

        // 用户信息存入Session
        CurrentSignerDTO currentSignerDTO = new CurrentSignerDTO();
        currentSignerDTO.setSessionId(sessionId);
        currentSignerDTO.setUser(user);
        currentSignerDTO.setSignedAt(LocalDateTime.now());
        currentSignerDTO.setPermissions(permissions);

        Sessions.set(SIGNER_SESSION_KEY, currentSignerDTO);

        // 登录状态（用户信息+会话ID）存入Redis
        redisTemplate.opsForHash().put(SIGN_STATUS_BY_USER_ID + user.getId(), sessionId, currentSignerDTO);

        // profile
        return new SignerProfileVO(user.getName(), permissionIds);
    }

    /**
     * 登出
     */
    @Override
    public void signOut() {
        Long userId = SignContext.userId();
        String sessionId = WebContext.getSession().getId();
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

    private UserEntity signCheck(SignInput input) {
        // 验证码校验
        String token = input.getCaptchaToken();
        String captcha = (String) redisTemplate.opsForValue().get(token);
        redisTemplate.delete(token);
        if (captcha == null) {
            throw new BizException("验证码超时");
        }
        if (!captcha.equalsIgnoreCase(input.getCaptcha())) {
            throw new BizException("验证码错误");
        }

        // 重复登录校验
        if (SignContext.isSigning()) {
            throw new BizException("已登录，请勿重复登录");
        }

        // 用户名密码校验
        UserEntity user;
        try {
            user = tryGetUser(input.getPrincipal());
            checkPassword(user, input.getPassword());
        } catch (UserNotExistException | PasswordIncorretException e) {
            throw new BizException("用户不存在或是密码错误");
        }
        return user;
    }

    private UserEntity tryGetUser(String principal) throws UserNotExistException {
        Optional<UserEntity> userOpt = userService.searchOneByPrincipal(principal);
        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            log.info("用户不存在 {}", principal);
            throw new UserNotExistException();
        }
    }

    private void checkPassword(UserEntity user, String inputPassword) throws PasswordIncorretException {
        String salt = user.getSalt();
        String inputPasswordEx = DigestUtils.sha512Hex(inputPassword);
        if (!DigestUtils.sha512Hex(inputPasswordEx + salt).equals(user.getPassword())) {
            log.info("用户密码错误 {} {}", user.getId(), inputPassword);
            throw new PasswordIncorretException();
        }
    }

}
