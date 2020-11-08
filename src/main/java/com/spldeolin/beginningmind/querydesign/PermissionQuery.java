package com.spldeolin.beginningmind.querydesign;

import java.util.Date;
import java.util.List;
import com.spldeolin.beginningmind.entity.PermissionEntity;
import com.spldeolin.beginningmind.support.QueryPredicate;

/**
 * 权限
 * <p>permission
 *
 * <p><p><strong>该类型由Allison 1875生成，请勿人为修改</strong>
 *
 * @author Deolin 2018/11/15
 */
public class PermissionQuery {

    private PermissionQuery() {
    }

    public static PermissionQuery design(String methodName) {
        throw new UnsupportedOperationException(methodName);
    }

    /**
     * 主键
     * <p>id
     * <p>不能为null
     */
    public QueryPredicate<PermissionQuery, Long> id;

    /**
     * 删除标记
     * <p>delete_flag
     * <p>不能为null
     * <p>默认：'0'
     */
    public QueryPredicate<PermissionQuery, Boolean> deleteFlag;

    /**
     * 插入数据的时间
     * <p>inserted_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    public QueryPredicate<PermissionQuery, Date> insertedAt;

    /**
     * 最近一次更新数据的时间。如果数据从未更新过，与inserted_at保持一致
     * <p>updated_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    public QueryPredicate<PermissionQuery, Date> updatedAt;

    /**
     * 映射方法（HTTP方法，e.g.: POST）
     * <p>mapping_method
     * <p>长度：255
     */
    public QueryPredicate<PermissionQuery, String> mappingMethod;

    /**
     * 映射路由（控制器路由+方法路由，e.g.: /user/create）
     * <p>mapping_path
     * <p>长度：255
     */
    public QueryPredicate<PermissionQuery, String> mappingPath;

    /**
     * 用于展示的名称
     * <p>display
     * <p>长度：255
     */
    public QueryPredicate<PermissionQuery, String> display;

    /**
     * 是否所有用户都拥有本权限
     * <p>is_granted_for_all
     */
    public QueryPredicate<PermissionQuery, Boolean> isGrantedForAll;

    /**
     * 是否所有用户都不拥有本权限
     * <p>is_forbidden
     */
    public QueryPredicate<PermissionQuery, Boolean> isForbidden;

    public List<PermissionEntity> over() {
        throw new UnsupportedOperationException();
    }
    /*{"entityQualifier":"com.spldeolin.beginningmind.entity.PermissionEntity","entityName":"PermissionEntity",
    "mapperQualifier":"com.spldeolin.beginningmind.mapper.PermissionMapper","mapperName":"PermissionMapper",
    "mapperRelativePath":"src/main/resources/mapper/PermissionMapper.xml","propertyNames":["id","deleteFlag",
    "insertedAt","updatedAt","mappingMethod","mappingPath","display","isGrantedForAll","isForbidden"],
    "tableName":"permission"}*/
}
