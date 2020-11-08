package com.spldeolin.beginningmind.serviceimpl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.constant.CoupledConstant;
import com.spldeolin.beginningmind.entity.UserEntity;
import com.spldeolin.beginningmind.exception.BizException;
import com.spldeolin.beginningmind.mapper.PermissionMapper;
import com.spldeolin.beginningmind.mapper.UserMapper;
import com.spldeolin.beginningmind.service.SnowFlakeService;
import com.spldeolin.beginningmind.service.UserService;
import com.spldeolin.beginningmind.util.StringRandomUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2018/11/12
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SnowFlakeService snowFlakeService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Long createUser(UserEntity user) {
        checkOccupationForCreating(user);

        // 盐、密码
        String salt = StringRandomUtils.generateVisibleAscii(32);
        user.setSalt(salt);
        String password = DigestUtils.sha512Hex(CoupledConstant.DEFAULT_PASSWORD_EX + salt);
        user.setPassword(password);

        // 能否登录
        user.setEnableSign(true);

        // 编号
        user.setSerialNumber(String.valueOf(snowFlakeService.nextId()));

        userMapper.insert(user);
        return user.getId();
    }

    /**
     * 创建场合下的用户名、手机号、E-Mail占用校验
     */
    private void checkOccupationForCreating(UserEntity user) {
        if (userMapper.getIdByName(user.getName()).isPresent()) {
            throw new BizException("用户名已被占用");
        }
        String mobile = user.getMobile();
        if (mobile != null && userMapper.getIdByMobile(mobile).isPresent()) {
            throw new BizException("手机号已被占用");
        }
        String email = user.getEmail();
        if (email != null && userMapper.getIdByEmail(email).isPresent()) {
            throw new BizException("E-Mail已被占用");
        }
    }

}
