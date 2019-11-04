package com.spldeolin.beginningmind.repository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spldeolin.beginningmind.common.CommonRepository;
import com.spldeolin.beginningmind.entity.User2permissionEntity;

/**
 * @author Deolin 2019-05-30
 */
@Component
public class User2permissionRepo extends CommonRepository<User2permissionEntity> {

    public List<Long> searchPermissionIdByUserId(Long userId) {
        LambdaQueryWrapper<User2permissionEntity> query = new LambdaQueryWrapper<>();
        query.select(User2permissionEntity::getPermissionId);
        query.eq(User2permissionEntity::getUserId, userId);

        List<User2permissionEntity> entities = super.searchBatch(query);
        return entities.stream().map(User2permissionEntity::getUserId).collect(Collectors.toList());
    }

}
