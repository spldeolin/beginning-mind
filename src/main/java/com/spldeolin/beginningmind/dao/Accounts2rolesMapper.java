package com.spldeolin.beginningmind.dao;

import com.spldeolin.beginningmind.api.CommonMapper;
import com.spldeolin.beginningmind.model.Accounts2roles;

import org.apache.ibatis.annotations.Mapper;
/**
 * “帐号与权限的关联”数据库映射
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Mapper
public interface Accounts2rolesMapper extends CommonMapper<Accounts2roles> {
}