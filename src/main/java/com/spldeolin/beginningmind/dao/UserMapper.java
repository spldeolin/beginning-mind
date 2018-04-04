package com.spldeolin.beginningmind.dao;

import com.spldeolin.beginningmind.model.User;
import com.spldeolin.cadeau.library.inherited.CommonMapper;

import org.apache.ibatis.annotations.Mapper;
/**
 * “用户”数据库映射
 *
 * @author Deolin 2018/4/4
 * @generator Cadeau Support
 */
@Mapper
public interface UserMapper extends CommonMapper<User> {
}