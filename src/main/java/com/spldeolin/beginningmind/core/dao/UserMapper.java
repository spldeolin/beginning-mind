package com.spldeolin.beginningmind.core.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spldeolin.beginningmind.core.entity.UserEntity;

/**
 * 用户
 *
 * @author Deolin 2018/11/15
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    @Select("select * from user")
    List<UserEntity> pages(@Param("page") Page<UserEntity> param);

}