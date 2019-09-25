package com.spldeolin.beginningmind.service;

import java.util.List;
import com.spldeolin.beginningmind.entity.UserEntity;

/**
 * 用户
 *
 * @author Deolin 2018/11/12
 */
public interface UserService {

    Long createUser(UserEntity user);

    UserEntity getUser(Long id);

    void updateUser(UserEntity user);

    void deleteUser(Long id);

    String deleteUsers(List<Long> ids);

    /**
     * 启用/禁用用户
     */
    void banPick(Long userId);

}
