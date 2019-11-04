package com.spldeolin.beginningmind.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spldeolin.beginningmind.common.CommonRepository;
import com.spldeolin.beginningmind.entity.UserEntity;

/**
 * @author Deolin 2019-05-30
 */
@Component
public class UserRepo extends CommonRepository<UserEntity> {

    public List<UserEntity> searchBatch(Wrapper<UserEntity> query) {
        return super.searchBatch(query);
    }

    public Optional<UserEntity> searchFirstByNameOrMobileOrEmail(String nameOrMobileOrEmail) {
        LambdaQueryWrapper<UserEntity> query = new LambdaQueryWrapper<>();
        query.or().eq(UserEntity::getName, nameOrMobileOrEmail)
                .or().eq(UserEntity::getMobile, nameOrMobileOrEmail)
                .or().eq(UserEntity::getEmail, nameOrMobileOrEmail);
        List<UserEntity> users = super.searchBatch(query);

        if (users.size() == 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(0));
    }

    public boolean isExistByName(String name) {
        UserEntity entity = new UserEntity();
        entity.setName(name);
        return super.count(entity) > 0;
    }

    public boolean isExistByNameNeId(String name, Long id) {
        LambdaQueryWrapper<UserEntity> query = new LambdaQueryWrapper<>();
        query.select(UserEntity::getId);
        query.eq(UserEntity::getName, name);
        query.ne(UserEntity::getId, id);

        return super.searchBatch(query).size() > 0;
    }

    public boolean isExistByMobile(String mobile) {
        UserEntity entity = new UserEntity();
        entity.setMobile(mobile);
        return super.count(entity) > 0;
    }

    public boolean isExistByMobileNeId(String mobile, Long id) {
        LambdaQueryWrapper<UserEntity> query = new LambdaQueryWrapper<>();
        query.select(UserEntity::getId);
        query.eq(UserEntity::getMobile, mobile);
        query.ne(UserEntity::getId, id);

        return super.searchBatch(query).size() > 0;
    }

    public boolean isExistByEmail(String email) {
        UserEntity entity = new UserEntity();
        entity.setEmail(email);
        return super.count(entity) > 0;
    }

    public boolean isExistByEmailNeId(String email, Long id) {
        LambdaQueryWrapper<UserEntity> query = new LambdaQueryWrapper<>();
        query.select(UserEntity::getId);
        query.eq(UserEntity::getEmail, email);
        query.ne(UserEntity::getId, id);

        return super.searchBatch(query).size() > 0;
    }

}
