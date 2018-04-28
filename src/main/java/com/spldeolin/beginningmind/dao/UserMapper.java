package com.spldeolin.beginningmind.dao;

import org.apache.ibatis.annotations.Mapper;
import com.spldeolin.beginningmind.api.CommonMapper;
import com.spldeolin.beginningmind.model.User;

/**
 * “用户”数据库映射
 *
 * @author Deolin 2018/4/21
 * @generator Cadeau Support
 */
@Mapper
public interface UserMapper extends CommonMapper<User> {
}