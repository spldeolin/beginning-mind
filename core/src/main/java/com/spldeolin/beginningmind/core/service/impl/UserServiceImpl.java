package com.spldeolin.beginningmind.core.service.impl;

import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.core.common.BizException;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import com.spldeolin.beginningmind.core.repository.PermissionRepo;
import com.spldeolin.beginningmind.core.repository.UserRepo;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import com.spldeolin.beginningmind.core.service.SnowFlakeService;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;

/**
 * @author Deolin 2018/11/12
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SnowFlakeService snowFlakeService;

    @Autowired
    private PermissionRepo permissionRepo;

    @Autowired
    private UserRepo userRepo;

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

        userRepo.create(user);
        return user.getId();
    }

    @Override
    public UserEntity getUser(Long id) {
        return userRepo.get(id).orElseThrow(() -> new BizException("用户不存在或是已被删除"));
    }

    @Override
    public void updateUser(UserEntity user) {
        Long id = user.getId();

        if (!userRepo.get(id).isPresent()) {
            throw new BizException("用户不存在或是已被删除");
        }
        checkOccupationForUpdating(user);

        if (!userRepo.update(user)) {
            throw new BizException("用户数据过时");
        }
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepo.get(id).isPresent()) {
            throw new BizException("用户不存在或是已被删除");
        }
        userRepo.delete(id);
    }

    @Override
    public String deleteUsers(List<Long> ids) {
        List<UserEntity> exist = userRepo.list(ids);
        if (exist.size() < ids.size()) {
            throw new BizException("部分用户不存在或是已被删除");
        }
        userRepo.delete(ids);
        return "操作成功";
    }

    @Override
    public void banPick(Long userId) {
        UserEntity user = userRepo.get(userId).orElseThrow(() -> new BizException("用户不存在或是已被删除"));
        user.setEnableSign(!user.getEnableSign());

        userRepo.update(user);
    }

    /**
     * 创建场合下的用户名、手机号、E-Mail占用校验
     */
    private void checkOccupationForCreating(UserEntity user) {
        if (userRepo.isExistByName(user.getName())) {
            throw new BizException("用户名已被占用");
        }
        String mobile = user.getMobile();
        if (mobile != null && userRepo.isExistByMobile(user.getMobile())) {
            throw new BizException("手机号已被占用");
        }
        String email = user.getEmail();
        if (email != null && userRepo.isExistByEmail(user.getEmail())) {
            throw new BizException("E-Mail已被占用");
        }
    }

    /**
     * 更新场合下的用户名、手机号、E-Mail占用校验
     */
    private void checkOccupationForUpdating(UserEntity user) {
        Long id = user.getId();
        String username = user.getName();
        String mobile = user.getMobile();
        String email = user.getEmail();
        if (userRepo.isExistByNameNeId(username, id)) {
            throw new BizException("用户名已被占用");
        }

        if (mobile != null && userRepo.isExistByMobileNeId(mobile, id)) {
            throw new BizException("手机号已被占用");
        }

        if (email != null && userRepo.isExistByEmailNeId(email, id)) {
            throw new BizException("E-Mail已被占用");
        }
    }

}
