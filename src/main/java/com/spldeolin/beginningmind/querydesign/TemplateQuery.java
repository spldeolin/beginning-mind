package com.spldeolin.beginningmind.querydesign;

import java.util.Date;
import java.util.List;
import com.spldeolin.beginningmind.entity.TemplateEntity;
import com.spldeolin.beginningmind.support.QueryPredicate;

/**
 * 建表语句
 * <p>__template
 *
 * <p><p><strong>该类型由Allison 1875生成，请勿人为修改</strong>
 *
 * @author Deolin 2020-11-06
 */
public class TemplateQuery {

    private TemplateQuery() {
    }

    public static TemplateQuery design(String methodName) {
        throw new UnsupportedOperationException(methodName);
    }

    /**
     * 主键
     * <p>id
     * <p>不能为null
     */
    public QueryPredicate<TemplateQuery, Long> id;

    /**
     * 删除标记
     * <p>delete_flag
     * <p>不能为null
     * <p>默认：'0'
     */
    public QueryPredicate<TemplateQuery, Boolean> deleteFlag;

    /**
     * 插入数据的时间
     * <p>inserted_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    public QueryPredicate<TemplateQuery, Date> insertedAt;

    /**
     * 最近一次更新数据的时间。如果数据从未更新过，与inserted_at保持一致
     * <p>updated_at
     * <p>不能为null
     * <p>默认：CURRENT_TIMESTAMP
     */
    public QueryPredicate<TemplateQuery, Date> updatedAt;

    public List<TemplateEntity> over() {
        throw new UnsupportedOperationException();
    }
    /*{"entityQualifier":"com.spldeolin.beginningmind.entity.TemplateEntity","entityName":"TemplateEntity",
    "mapperQualifier":"com.spldeolin.beginningmind.mapper.TemplateMapper","mapperName":"TemplateMapper",
    "mapperRelativePath":"src/main/resources/mapper/TemplateMapper.xml","propertyNames":["id","deleteFlag",
    "insertedAt","updatedAt"],"tableName":"__template"}*/
}
