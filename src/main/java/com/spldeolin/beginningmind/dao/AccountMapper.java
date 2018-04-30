package com.spldeolin.beginningmind.dao;

import org.apache.ibatis.annotations.Mapper;
import com.spldeolin.beginningmind.api.CommonMapper;
import com.spldeolin.beginningmind.model.Account;

/**
 * “帐号（用于登录的信息）”数据库映射
 *
 * @author Deolin 2018/4/30
 * @generator Cadeau Support
 */
@Mapper
public interface AccountMapper extends CommonMapper<Account> {
}