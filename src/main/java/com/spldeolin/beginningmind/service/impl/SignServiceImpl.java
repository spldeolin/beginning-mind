package com.spldeolin.beginningmind.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.entity.PermissionEntity;
import com.spldeolin.beginningmind.entity.UserEntity;
import com.spldeolin.beginningmind.exception.BizException;
import com.spldeolin.beginningmind.extension.dto.RequestTrack;
import com.spldeolin.beginningmind.input.SignInput;
import com.spldeolin.beginningmind.mapper.UserMapper;
import com.spldeolin.beginningmind.security.dto.CurrentSignerDTO;
import com.spldeolin.beginningmind.security.util.SignContext;
import com.spldeolin.beginningmind.service.PermissionService;
import com.spldeolin.beginningmind.service.SignService;
import com.spldeolin.beginningmind.vo.SignerProfileVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin
 */
@Service
@Slf4j
public class SignServiceImpl implements SignService {

    public static final String SIGNER_SESSION_KEY = "signer";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionService permissionService;

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
        HttpSession session = RequestTrack.getCurrent().getRequest().getSession();
        String sessionId = session.getId();

        // 用户信息存入Session
        CurrentSignerDTO currentSignerDTO = new CurrentSignerDTO();
        currentSignerDTO.setSessionId(sessionId);
        currentSignerDTO.setUser(user);
        currentSignerDTO.setSignedAt(LocalDateTime.now());
        currentSignerDTO.setPermissions(permissions);

        session.setAttribute(SIGNER_SESSION_KEY, currentSignerDTO);

        // profile
        return new SignerProfileVO(user.getName(), permissionIds);
    }

    /**
     * 登出
     */
    @Override
    public void signOut() {
        RequestTrack.getCurrent().getRequest().getSession().removeAttribute(SIGNER_SESSION_KEY);
    }

    private UserEntity signCheck(SignInput input) {
        // 重复登录校验
        if (SignContext.isSigning()) {
            throw new BizException("已登录，请勿重复登录");
        }

        // 用户名密码校验
        UserEntity user = tryGetUser(input.getPrincipal());
        checkPassword(user, input.getPassword());

        return user;
    }

    private UserEntity tryGetUser(String principal) {
        Optional<UserEntity> userOpt = userMapper.getByNameOrMobileOrEmail(principal);
        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            log.info("用户不存在 {}", principal);
            throw new BizException("用户不存在或是密码错误");
        }
    }

    private void checkPassword(UserEntity user, String inputPassword) {
        String salt = user.getSalt();
        String inputPasswordEx = DigestUtils.sha512Hex(inputPassword);
        if (!DigestUtils.sha512Hex(inputPasswordEx + salt).equals(user.getPassword())) {
            log.info("用户的密码错误 {} {}", user.getId(), inputPassword);
            throw new BizException("用户不存在或是密码错误");
        }
    }

}
