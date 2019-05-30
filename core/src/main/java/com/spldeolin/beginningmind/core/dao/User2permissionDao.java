package com.spldeolin.beginningmind.core.dao;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spldeolin.beginningmind.core.common.CommonDao;
import com.spldeolin.beginningmind.core.entity.User2permissionEntity;

/**
 * @author Deolin 2019-05-30
 */
@Component
public class User2permissionDao extends CommonDao<User2permissionEntity> {

    @Autowired
    private User2permissionDao user2permissionDao;

    public List<Long> searchPermissionIdByUserId(Long userId) {
        LambdaQueryWrapper<User2permissionEntity> query = new LambdaQueryWrapper<>();
        query.select(User2permissionEntity::getPermissionId);
        query.eq(User2permissionEntity::getUserId, userId);

        List<User2permissionEntity> entities = user2permissionDao.searchBatch(query);
        return entities.stream().map(User2permissionEntity::getUserId).collect(Collectors.toList());
    }

}
