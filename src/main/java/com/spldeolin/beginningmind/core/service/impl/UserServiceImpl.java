package com.spldeolin.beginningmind.core.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.spldeolin.beginningmind.core.api.CommonServiceImpl;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.api.dto.PageParam;
import com.spldeolin.beginningmind.core.model.User;
import com.spldeolin.beginningmind.core.service.UserService;

/**
 * @author Deolin 2018/11/12
 */
@Service
public class UserServiceImpl extends CommonServiceImpl<User> implements UserService {

    // TODO 实现方法

    @Override
    public Long createEX(User user) {
        return null;
    }

    @Override
    public User getEX(Long id) {
        return null;
    }

    @Override
    public void updateEX(User user) {

    }

    @Override
    public void deleteEX(Long id) {

    }

    @Override
    public String deleteEX(List<Long> ids) {
        return null;
    }

    @Override
    public Page<User> page(PageParam pageParam) {
        return null;
    }

    @Override
    public Optional<User> searchOneByPrincipal(String principal) {
        return Optional.empty();
    }

    @Override
    public Set<String> listUserPermissions(Long userId) {
        return null;
    }

    @Override
    public Boolean isAccountSigning(Long userId) {
        return null;
    }

    @Override
    public void killSigner(Long userId) {

    }

    @Override
    public void banPick(Long userId) {

    }

}
