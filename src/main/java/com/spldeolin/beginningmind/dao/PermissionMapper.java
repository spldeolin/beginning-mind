package com.spldeolin.beginningmind.dao;

import com.spldeolin.beginningmind.api.CommonMapper;
import com.spldeolin.beginningmind.model.Permission;

import org.apache.ibatis.annotations.Mapper;
/**
 * “权限”数据库映射
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Mapper
public interface PermissionMapper extends CommonMapper<Permission> {
}