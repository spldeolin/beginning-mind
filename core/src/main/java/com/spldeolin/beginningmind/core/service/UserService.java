package com.spldeolin.beginningmind.core.service;

import java.util.List;
import java.util.Optional;
import com.spldeolin.beginningmind.core.common.CommonDao;
import com.spldeolin.beginningmind.core.entity.UserEntity;

/**
 * 用户
 *
 * @author Deolin 2018/11/12
 */
public interface UserService extends CommonDao<UserEntity> {

    /**
     * 创建一个“用户” （附带业务校验）
     *
     * @param user 待创建“用户”
     * @return 自增ID
     */
    Long createEX(UserEntity user);

    /**
     * 获取一个“用户” （附带业务校验）
     *
     * @param id “用户”的ID
     * @return “用户”
     */
    UserEntity getEX(Long id);

    /**
     * 更新一个“用户” （附带业务校验）
     *
     * @param user 待更新“用户”
     */
    void updateEX(UserEntity user);

    /**
     * 删除一个“用户”
     *
     * @param id 待删除“用户”的ID
     */
    void deleteEX(Long id);

    /**
     * 删除多个资源 （附带业务校验，并返回详细情况）
     *
     * @param ids 待删除资源的ID列表
     * @return 删除情况
     */
    String deleteEX(List<Long> ids);

    /**
     * 通过用户名或手机号或email搜索用户
     */
    Optional<UserEntity> searchOneByPrincipal(String principal);

    /**
     * 启用/禁用用户
     */
    void banPick(Long userId);

}
