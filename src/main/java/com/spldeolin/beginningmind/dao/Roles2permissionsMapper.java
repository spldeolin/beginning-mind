package com.spldeolin.beginningmind.dao;

import com.spldeolin.beginningmind.api.CommonMapper;
import com.spldeolin.beginningmind.model.Roles2permissions;

import org.apache.ibatis.annotations.Mapper;
/**
 * “角色与权限的关联”数据库映射
 *
 * @author Deolin 2018/5/1
 * @generator Cadeau Support
 */
@Mapper
public interface Roles2permissionsMapper extends CommonMapper<Roles2permissions> {
}