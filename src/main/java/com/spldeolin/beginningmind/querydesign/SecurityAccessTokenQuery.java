package com.spldeolin.beginningmind.querydesign;

import java.util.Date;
import java.util.List;
import com.spldeolin.beginningmind.entity.SecurityAccessTokenEntity;
import com.spldeolin.beginningmind.support.QueryPredicate;

/**
 * 代表声明了@SecurityAccess(AccessMode.TOKEN)的请求方法的token值
 * <p>security_access_token
 *
 * <p><p><strong>该类型由Allison 1875生成，请勿人为修改</strong>
 *
 * @author Deolin 2019-02-23
 */
public class SecurityAccessTokenQuery {

    private SecurityAccessTokenQuery() {
    }

    public static SecurityAccessTokenQuery design(String methodName) {
        throw new UnsupportedOperationException(methodName);
    }

    /**
     * 主键
     * <p>id
     * <p>不能为null
     */
    public QueryPredicate<SecurityAccessTokenQuery, Long> id;

    /**
     * 删除标记
     * <p>delete_flag
     * <p>不能为null
     * <p>默认：'0'
     */
    public QueryPredicate<SecurityAccessTokenQuery, Boolean> deleteFlag;

    /**
     * 插入数据的时间
     * <p>inserted_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    public QueryPredicate<SecurityAccessTokenQuery, Date> insertedAt;

    /**
     * 最近一次更新数据的时间。如果数据从未更新过，与inserted_at保持一致
     * <p>updated_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    public QueryPredicate<SecurityAccessTokenQuery, Date> updatedAt;

    /**
     * 映射方法（HTTP方法，e.g.: POST）
     * <p>mapping_method
     * <p>长度：255
     */
    public QueryPredicate<SecurityAccessTokenQuery, String> mappingMethod;

    /**
     * 映射路由（控制器路由+方法路由，e.g.: /user/create）
     * <p>mapping_path
     * <p>长度：255
     */
    public QueryPredicate<SecurityAccessTokenQuery, String> mappingPath;

    /**
     * TOKEN（30位随机大小写字母+数字）
     * <p>token
     * <p>长度：30
     */
    public QueryPredicate<SecurityAccessTokenQuery, String> token;

    public List<SecurityAccessTokenEntity> over() {
        throw new UnsupportedOperationException();
    }
    /*{"entityQualifier":"com.spldeolin.beginningmind.entity.SecurityAccessTokenEntity",
    "entityName":"SecurityAccessTokenEntity","mapperQualifier":"com.spldeolin.beginningmind.mapper
    .SecurityAccessTokenMapper","mapperName":"SecurityAccessTokenMapper",
    "mapperRelativePath":"src/main/resources/mapper/SecurityAccessTokenMapper.xml","propertyNames":["id",
    "deleteFlag","insertedAt","updatedAt","mappingMethod","mappingPath","token"],"tableName":"security_access_token"}*/
}
