package com.spldeolin.beginningmind.mapper;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spldeolin.beginningmind.entity.UserEntity;

/**
 * 用户
 *
 * @author Deolin 2018/11/15
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    Optional<UserEntity> getByNameOrMobileOrEmail(String nameOrMobileOrEmail);

    List<UserEntity> listAsPageByMobile(Page<UserEntity> param, @Param("mobile") String mobile);

}