package com.spldeolin.beginningmind.dao;

import org.apache.ibatis.annotations.Mapper;
import com.spldeolin.beginningmind.api.CommonMapper;
import com.spldeolin.beginningmind.model.SecurityAccounts2roles;

/**
 * “帐号与权限的关联”数据库映射
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Mapper
public interface SecurityAccounts2rolesMapper extends CommonMapper<SecurityAccounts2roles> {
}