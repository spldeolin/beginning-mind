package com.spldeolin.beginningmind.core.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spldeolin.beginningmind.core.api.CommonServiceImpl;
import com.spldeolin.beginningmind.core.api.exception.BizException;
import com.spldeolin.beginningmind.core.config.SessionConfig;
import com.spldeolin.beginningmind.core.constant.CoupledConstant;
import com.spldeolin.beginningmind.core.dao.UserMapper;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.service.PermissionService;
import com.spldeolin.beginningmind.core.service.SnowFlakeService;
import com.spldeolin.beginningmind.core.service.UserService;
import com.spldeolin.beginningmind.core.util.StringRandomUtils;

/**
 * @author Deolin 2018/11/12
 */
@Service
public class UserServiceImpl extends CommonServiceImpl<User> implements UserService {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private FindByIndexNameSessionRepository<? extends Session> finder;

    @Autowired
    private SnowFlakeService snowFlakeService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Long createEX(User user) {
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
    public User getEX(Long id) {
        return super.get(id).orElseThrow(() -> new BizException("用户不存在或是已被删除"));
    }

    @Override
    public void updateEX(User user) {
        Long id = user.getId();

        if (!isExist(id)) {
            throw new BizException("用户不存在或是已被删除");
        }
        if (getSignerSession(id).isPresent()) {
            throw new BizException("用户登录中，等待用户退出或是将用户请离后再次操作");
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
        if (getSignerSession(id).isPresent()) {
            throw new BizException("用户登录中，等待用户退出或是将用户请离后再次操作");
        }
        super.delete(id);
    }

    @Override
    public String deleteEX(List<Long> ids) {
        List<User> exist = super.list(ids);
        if (exist.size() < ids.size()) {
            throw new BizException("部分用户不存在或是已被删除");
        }
        for (Long id : ids) {
            if (getSignerSession(id).isPresent()) {
                throw new BizException("部分用户登录中，无法删除");
            }
        }
        super.delete(ids);
        return "操作成功";
    }

    @Override
    public Optional<User> searchOneByPrincipal(String principal) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.or().eq("name", principal).or().eq("mobile", principal).or().eq("email", principal);
        List<User> users = super.searchBatch(query);
        if (users.size() == 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(0));
    }

    @Override
    public Boolean isAccountSigning(Long userId) {
        return getSignerSession(userId).isPresent();
    }

    @Override
    public void killSigner(Long userId) {
        Session session = getSignerSession(userId).orElseThrow(() -> new BizException("用户已离线"));
        // 被踢登录者 会在切面中通过自身的当前会话ID找个这个标识，找到后直接调用Shiro登出
        redisTemplate.opsForValue().set("killed:session:" + session.getId(), "killed",
                SessionConfig.SESSION_EXPIRE_SECONDS, TimeUnit.SECONDS);
    }

    @Override
    public void banPick(Long userId) {
        User user = get(userId).orElseThrow(() -> new BizException("用户不存在或是已被删除"));
        super.update(user.setEnableSign(!user.getEnableSign()));
    }

    /**
     * 根据登录时存的PRINCIPAL_NAME_INDEX_NAME的值，通过Spring Session提供的API找到登录者的会话， 会话不存在则代表未登录
     */
    private Optional<Session> getSignerSession(Long userId) {
        Collection<? extends Session> signerSessions = this.finder.findByIndexNameAndIndexValue(
                FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, userId.toString()).values();
        // 只可能找到一个，或找不到
        if (signerSessions.size() > 0) {
            return Optional.ofNullable(signerSessions.toArray(new Session[0])[0]);
        } else {
            return Optional.empty();
        }
    }

    /**
     * 创建场合下的用户名、手机号、E-Mail占用校验
     */
    private void checkOccupationForCreating(User user) {
        if (searchOne(User::getName, user.getName()).isPresent()) {
            throw new BizException("用户名已被占用");
        }
        String mobile = user.getMobile();
        if (mobile != null && searchOne(User::getMobile, mobile).isPresent()) {
            throw new BizException("手机号已被占用");
        }
        String email = user.getEmail();
        if (email != null && searchOne(User::getEmail, email).isPresent()) {
            throw new BizException("E-Mail已被占用");
        }
    }

    /**
     * 更新场合下的用户名、手机号、E-Mail占用校验
     */
    private void checkOccupationForUpdating(User user) {
        Long id = user.getId();
        String username = user.getName();
        if (!id.equals(searchOne(User::getName, username).orElse(new User()).getId())) {
            throw new BizException("用户名已被占用");
        }
        String mobile = user.getMobile();
        if (mobile != null) {
            if (!id.equals(searchOne(User::getMobile, mobile).orElse(new User()).getId())) {
                throw new BizException("手机号已被占用");
            }
        }
        String email = user.getEmail();
        if (email != null) {
            if (!id.equals(searchOne(User::getEmail, email).orElse(new User()).getId())) {
                throw new BizException("E-Mail已被占用");
            }
        }
    }

}
