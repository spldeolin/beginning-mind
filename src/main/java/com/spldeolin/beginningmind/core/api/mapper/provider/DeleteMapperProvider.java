package com.spldeolin.beginningmind.core.api.mapper.provider;

import java.util.Set;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.util.ReflectionUtils;
import com.spldeolin.beginningmind.core.util.SqlUtils;
import com.spldeolin.beginningmind.core.util.StringCaseUtils;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.provider.IdsProvider;
import tk.mybatis.mapper.provider.base.BaseUpdateProvider;

@Log4j2
public class DeleteMapperProvider extends MapperTemplate {

    public DeleteMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * @see BaseUpdateProvider#updateByPrimaryKey(MappedStatement)
     */
    public String deleteById(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        if (ReflectionUtils.findField(entityClass, StringCaseUtils.snakeToLowerCamel("is_deleted")) == null) {
            log.warn("Model[" + entityClass.getCanonicalName() + "]中没有删除标识字段[" + "is_deleted" +
                    "]，调用mapper.deleted()将会抛出异常");
            return null;
        }
        String sql = SqlUtils.updateTable(entityClass, tableName(entityClass));
        sql += SqlUtils.deleteFlagWhenDelete();
        sql += SqlUtils.wherePKColumns(entityClass, false); // 不使用乐观锁
        return sql;
    }

    /**
     * @see IdsProvider#deleteByIds(MappedStatement)
     */
    public String deleteBatchByIds(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        if (ReflectionUtils.findField(entityClass, StringCaseUtils.snakeToLowerCamel("is_deleted")) == null) {
            log.warn("Model[" + entityClass + "]中没有删除标识字段[" + "is_deleted" + "]，调用mapper.deleted()将会抛出异常");
            return null;
        }
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        String sql = SqlUtils.updateTable(entityClass, tableName(entityClass));
        if (columnList.size() == 1) {
            sql += SqlUtils.deleteFlagWhenDelete();
            sql += " where ";
            sql += columnList.iterator().next().getColumn();
            sql += " in (${_parameter})";
            sql += " and " + "is_deleted" + "=FALSE";
        } else {
            throw new MapperException(
                    "继承 deleteByIds 方法的实体类[" + entityClass.getCanonicalName() + "]中必须有且只有一个带有@Id注解的字段");
        }
        return sql;
    }

}