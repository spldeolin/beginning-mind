package com.spldeolin.beginningmind.querydesign;

import java.util.Date;
import java.util.List;
import com.spldeolin.beginningmind.entity.UserEntity;
import com.spldeolin.beginningmind.support.QueryPredicate;

/**
 * 用户
 * <p>user
 *
 * <p><p><strong>该类型由Allison 1875生成，请勿人为修改</strong>
 *
 * @author Deolin 2018/11/15
 */
public class UserQuery {

    private UserQuery() {
    }

    public static UserQuery design(String methodName) {
        throw new UnsupportedOperationException(methodName);
    }

    /**
     * 主键
     * <p>id
     * <p>不能为null
     */
    public QueryPredicate<UserQuery, Long> id;

    /**
     * 删除标记
     * <p>delete_flag
     * <p>不能为null
     * <p>默认：'0'
     */
    public QueryPredicate<UserQuery, Boolean> deleteFlag;

    /**
     * 插入数据的时间
     * <p>inserted_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    public QueryPredicate<UserQuery, Date> insertedAt;

    /**
     * 最近一次更新数据的时间。如果数据从未更新过，与inserted_at保持一致
     * <p>updated_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    public QueryPredicate<UserQuery, Date> updatedAt;

    /**
     * 用户编号
     * <p>serial_number
     * <p>长度：255
     */
    public QueryPredicate<UserQuery, String> serialNumber;

    /**
     * 名字
     * <p>name
     * <p>长度：255
     */
    public QueryPredicate<UserQuery, String> name;

    /**
     * 手机号
     * <p>mobile
     * <p>长度：20
     */
    public QueryPredicate<UserQuery, String> mobile;

    /**
     * E-mail
     * <p>email
     * <p>长度：255
     */
    public QueryPredicate<UserQuery, String> email;

    /**
     * 密码
     * <p>password
     * <p>长度：128
     */
    public QueryPredicate<UserQuery, String> password;

    /**
     * 盐
     * <p>salt
     * <p>长度：32
     */
    public QueryPredicate<UserQuery, String> salt;

    /**
     * 能否登录
     * <p>enable_sign
     */
    public QueryPredicate<UserQuery, Boolean> enableSign;

    public List<UserEntity> over() {
        throw new UnsupportedOperationException();
    }
    /*{"entityQualifier":"com.spldeolin.beginningmind.entity.UserEntity","entityName":"UserEntity",
    "mapperQualifier":"com.spldeolin.beginningmind.mapper.UserMapper","mapperName":"UserMapper",
    "mapperRelativePath":"src/main/resources/mapper/UserMapper.xml","propertyNames":["id","deleteFlag","insertedAt",
    "updatedAt","serialNumber","name","mobile","email","password","salt","enableSign"],"tableName":"user"}*/
}
