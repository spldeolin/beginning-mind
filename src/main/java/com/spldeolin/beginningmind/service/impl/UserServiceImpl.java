package com.spldeolin.beginningmind.service.impl;

import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.exception.BizException;
import com.spldeolin.beginningmind.constant.CoupledConstant;
import com.spldeolin.beginningmind.entity.UserEntity;
import com.spldeolin.beginningmind.mapper.PermissionMapper;
import com.spldeolin.beginningmind.mapper.UserMapper;
import com.spldeolin.beginningmind.service.SnowFlakeService;
import com.spldeolin.beginningmind.service.UserService;
import com.spldeolin.beginningmind.util.StringRandomUtils;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/11/12
 */
@Log4j2
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

    @Override
    public UserEntity getUser(Long id) {
        UserEntity userEntity = userMapper.selectById(id);
        if (userEntity == null) {
            throw new BizException("用户不存在或是已被删除");
        }
        return userEntity;
    }

    @Override
    public void updateUser(UserEntity user) {
        checkOccupationForUpdating(user);
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public String deleteUsers(List<Long> ids) {
        userMapper.deleteBatchIds(ids);
        return "操作成功";
    }

    @Override
    public void banPick(Long userId) {
        UserEntity user = userMapper.selectById(userId);
        if (user != null) {
            user.setEnableSign(!user.getEnableSign());
            if (userMapper.updateById(user) == 0) {
                log.warn("乐观锁冲突");
            }
        } else {
            log.warn("user[{}]不存在或是已被删除", userId);
        }
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

    /**
     * 更新场合下的用户名、手机号、E-Mail占用校验
     */
    private void checkOccupationForUpdating(UserEntity user) {
        String username = user.getName();
        String mobile = user.getMobile();
        String email = user.getEmail();

        userMapper.getIdByName(username).ifPresent(id -> {
            if (!id.equals(user.getId())) {
                throw new BizException("用户名已被占用");
            }
        });

        userMapper.getIdByMobile(mobile).ifPresent(id -> {
            if (!id.equals(user.getId())) {
                throw new BizException("手机号已被占用");
            }
        });

        userMapper.getIdByEmail(email).ifPresent(id -> {
            if (!id.equals(user.getId())) {
                throw new BizException("E-Mail已被占用");
            }
        });
    }

}
