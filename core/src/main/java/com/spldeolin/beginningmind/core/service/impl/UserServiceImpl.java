package com.spldeolin.beginningmind.core.service.impl;

import java.util.List;
import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spldeolin.beginningmind.core.common.BizException;
import com.spldeolin.beginningmind.core.common.CommonDaoImpl;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import com.spldeolin.beginningmind.core.mapper.UserMapper;
import com.spldeolin.beginningmind.core.entity.UserEntity;
import com.spldeolin.beginningmind.core.service.PermissionService;
import com.spldeolin.beginningmind.core.service.SnowFlakeService;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;

/**
 * @author Deolin 2018/11/12
 */
@Service
public class UserServiceImpl extends CommonDaoImpl<UserEntity> implements UserService {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SnowFlakeService snowFlakeService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Long createEX(UserEntity user) {
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

        super.create(user);
        return user.getId();
    }

    @Override
    public UserEntity getEX(Long id) {
        return super.get(id).orElseThrow(() -> new BizException("用户不存在或是已被删除"));
    }

    @Override
    public void updateEX(UserEntity user) {
        Long id = user.getId();

        if (!isExist(id)) {
            throw new BizException("用户不存在或是已被删除");
        }
        checkOccupationForUpdating(user);

        if (!super.update(user)) {
            throw new BizException("用户数据过时");
        }
    }

    @Override
    public void deleteEX(Long id) {
        if (!isExist(id)) {
            throw new BizException("用户不存在或是已被删除");
        }
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<UserEntity> exist = super.list(ids);
        if (exist.size() < ids.size()) {
            throw new BizException("部分用户不存在或是已被删除");
        }
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Optional<UserEntity> searchOneByPrincipal(String principal) {
        QueryWrapper<UserEntity> query = new QueryWrapper<>();
        query.or().eq("name", principal).or().eq("mobile", principal).or().eq("email", principal);
        List<UserEntity> users = super.searchBatch(query);
        if (users.size() == 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(0));
    }

    @Override
    public void banPick(Long userId) {
        UserEntity user = get(userId).orElseThrow(() -> new BizException("用户不存在或是已被删除"));
        user.setEnableSign(!user.getEnableSign());

        super.update(user);
    }

    /**
     * 创建场合下的用户名、手机号、E-Mail占用校验
     */
    private void checkOccupationForCreating(UserEntity user) {
        if (searchOne(UserEntity::getName, user.getName()).isPresent()) {
            throw new BizException("用户名已被占用");
        }
        String mobile = user.getMobile();
        if (mobile != null && searchOne(UserEntity::getMobile, mobile).isPresent()) {
            throw new BizException("手机号已被占用");
        }
        String email = user.getEmail();
        if (email != null && searchOne(UserEntity::getEmail, email).isPresent()) {
            throw new BizException("E-Mail已被占用");
        }
    }

    /**
     * 更新场合下的用户名、手机号、E-Mail占用校验
     */
    private void checkOccupationForUpdating(UserEntity user) {
        Long id = user.getId();
        String username = user.getName();
        if (!id.equals(searchOne(UserEntity::getName, username).orElse(new UserEntity()).getId())) {
            throw new BizException("用户名已被占用");
        }
        String mobile = user.getMobile();
        if (mobile != null) {
            if (!id.equals(searchOne(UserEntity::getMobile, mobile).orElse(new UserEntity()).getId())) {
                throw new BizException("手机号已被占用");
            }
        }
        String email = user.getEmail();
        if (email != null) {
            if (!id.equals(searchOne(UserEntity::getEmail, email).orElse(new UserEntity()).getId())) {
                throw new BizException("E-Mail已被占用");
            }
        }
    }

}
