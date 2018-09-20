package com.spldeolin.beginningmind.core.api.mapper.provider;

import java.util.Set;
import org.apache.ibatis.mapping.MappedStatement;
import com.spldeolin.beginningmind.core.api.mapper.constant.AuditField;
import com.spldeolin.beginningmind.core.api.mapper.util.SqlUtils;
import com.spldeolin.beginningmind.core.api.mapper.util.StringCompressUtils;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

/**
 * @author Deolin 2018/09/20
 */
@Log4j2
public class ClearProvider extends MapperTemplate {

    public ClearProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String clearProperty(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder(SqlUtils.updateTable(entityClass, tableName(entityClass)) + "SET");
        for (EntityColumn column : columnList) {
            sql.append("<if test=\"propertyName=='").append(column.getProperty()).append("'\">");
            sql.append(column.getColumn()).append("=null ");
            sql.append("</if>");
        }
        sql.append(" WHERE" + AuditField.IS_NOT_DELETED + "AND id = #{id}");
        log.debug("Provide Mapper Statement: " + StringCompressUtils.trimUnnecessaryBlanks(sql.toString()));
        return sql.toString();
    }

}
