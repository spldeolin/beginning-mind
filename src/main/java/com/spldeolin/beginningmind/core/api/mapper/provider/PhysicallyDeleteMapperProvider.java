package com.spldeolin.beginningmind.core.api.mapper.provider;

import java.util.Set;
import org.apache.ibatis.mapping.MappedStatement;
import com.spldeolin.beginningmind.core.api.mapper.util.SqlUtils;
import com.spldeolin.beginningmind.core.api.mapper.util.StringCompressUtils;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.provider.base.BaseDeleteProvider;

/**
 * @author Deolin
 */
@Log4j2
public class PhysicallyDeleteMapperProvider extends MapperTemplate {

    public PhysicallyDeleteMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * @see BaseDeleteProvider#deleteByPrimaryKey(MappedStatement)
     */
    public String physicallyDeleteById(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        String sql = SqlUtils.deleteFromTable(entityClass, tableName(entityClass));
        sql += SqlUtils.wherePKColumns(entityClass, false); // 不使用乐观锁
        log.debug("Provide Mapper Statement: " + StringCompressUtils.trimUnnecessaryBlanks(sql));
        return sql;
    }

    public String physicallyDeleteByIds(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder(SqlUtils.deleteFromTable(entityClass, tableName(entityClass)));
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        if (columnList.size() == 1) {
            EntityColumn column = columnList.iterator().next();
            sql.append(" where ");
            sql.append(column.getColumn());
            sql.append(" in (${_parameter})");
        } else {
            throw new MapperException(
                    "继承 selectByIds 方法的实体类[" + entityClass.getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
        }
        log.debug("Provide Mapper Statement: " + StringCompressUtils.trimUnnecessaryBlanks(sql));
        return sql.toString();
    }
}
