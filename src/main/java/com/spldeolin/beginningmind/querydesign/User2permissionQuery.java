package com.spldeolin.beginningmind.querydesign;

import java.util.Date;
import java.util.List;
import com.spldeolin.beginningmind.entity.User2permissionEntity;
import com.spldeolin.beginningmind.support.QueryPredicate;

/**
 * 用户与权限的关联关系
 * <p>user2permission
 *
 * <p><p><strong>该类型由Allison 1875生成，请勿人为修改</strong>
 *
 * @author Deolin 2018/12/07
 */
public class User2permissionQuery {

    private User2permissionQuery() {
    }

    public static User2permissionQuery design(String methodName) {
        throw new UnsupportedOperationException(methodName);
    }

    /**
     * 主键
     * <p>id
     * <p>不能为null
     */
    public QueryPredicate<User2permissionQuery, Long> id;

    /**
     * 删除标记
     * <p>delete_flag
     * <p>不能为null
     * <p>默认：'0'
     */
    public QueryPredicate<User2permissionQuery, Boolean> deleteFlag;

    /**
     * 插入数据的时间
     * <p>inserted_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    public QueryPredicate<User2permissionQuery, Date> insertedAt;

    /**
     * 最近一次更新数据的时间。如果数据从未更新过，与inserted_at保持一致
     * <p>updated_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    public QueryPredicate<User2permissionQuery, Date> updatedAt;

    /**
     * 用户ID
     * <p>user_id
     */
    public QueryPredicate<User2permissionQuery, Long> userId;

    /**
     * 权限ID
     * <p>permission_id
     */
    public QueryPredicate<User2permissionQuery, Long> permissionId;

    public List<User2permissionEntity> over() {
        throw new UnsupportedOperationException();
    }
    /*{"entityQualifier":"com.spldeolin.beginningmind.entity.User2permissionEntity",
    "entityName":"User2permissionEntity","mapperQualifier":"com.spldeolin.beginningmind.mapper
    .User2permissionMapper","mapperName":"User2permissionMapper",
    "mapperRelativePath":"src/main/resources/mapper/User2permissionMapper.xml","propertyNames":["id","deleteFlag",
    "insertedAt","updatedAt","userId","permissionId"],"tableName":"user2permission"}*/
}
