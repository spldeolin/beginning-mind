package com.spldeolin.beginningmind.core.api.mapper.provider;

import java.util.Set;
import org.apache.ibatis.mapping.MappedStatement;
import com.spldeolin.beginningmind.core.util.SqlUtils;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.MapperException;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.provider.ConditionProvider;
import tk.mybatis.mapper.provider.ExampleProvider;
import tk.mybatis.mapper.provider.IdsProvider;
import tk.mybatis.mapper.provider.base.BaseSelectProvider;

@Log4j2
public class SelectMapperProvider extends MapperTemplate {

    public SelectMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * @see BaseSelectProvider#selectByPrimaryKey(MappedStatement)
     */
    public String selectById(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //将返回值修改为实体类型
        setResultType(ms, entityClass);
        String sql = SqlUtils.selectAllColumns(entityClass) +
                SqlUtils.fromTable(entityClass, tableName(entityClass)) +
                SqlUtils.wherePKColumns(entityClass);
        return sql;
    }

    /**
     * @see IdsProvider#selectByIds(MappedStatement)
     */
    public String selectBatchByIds(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtils.selectAllColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass, tableName(entityClass)));
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        if (columnList.size() == 1) {
            EntityColumn column = columnList.iterator().next();
            sql.append(" where ");
            sql.append(column.getColumn());
            sql.append(" in (${_parameter})");
            sql.append(" and " + "is_deleted" + "=FALSE");
        } else {
            throw new MapperException(
                    "继承 selectByIds 方法的实体类[" + entityClass.getCanonicalName() + "]中必须只有一个带有 @Id 注解的字段");
        }
        return sql.toString();
    }

    /**
     * @see BaseSelectProvider#selectAll(MappedStatement)
     */
    public String selectAll(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //修改返回值类型为实体类型
        setResultType(ms, entityClass);
        String sql = SqlUtils.selectAllColumns(entityClass) +
                SqlUtils.fromTable(entityClass, tableName(entityClass)) +
                " where " + "is_deleted" + "=FALSE " +
                SqlUtils.orderByDefault(entityClass);
        return sql;
    }

    /**
     * @see BaseSelectProvider#selectOne(MappedStatement)
     */
    public String selectBatchByModel(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);
        String sql = SqlUtils.selectAllColumns(entityClass) +
                SqlUtils.fromTable(entityClass, tableName(entityClass)) +
                SqlUtils.whereAllIfColumns(entityClass, isNotEmpty());
        return sql;
    }

    /**
     * @see ExampleProvider#selectByExample(MappedStatement)
     */
    public String selectBatchByCondition(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder("SELECT ");
        if (isCheckExampleEntityClass()) {
            sql.append(SqlUtils.exampleCheck(entityClass));
        }
        sql.append("<if test=\"distinct\">distinct</if>");
        sql.append(SqlUtils.exampleSelectColumns(entityClass));
        sql.append(SqlUtils.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlUtils.exampleWhereClause());
        sql.append(SqlUtils.exampleOrderBy(entityClass));
        sql.append(SqlUtils.exampleForUpdate());
        return sql.toString();
    }

    /**
     * @see BaseSelectProvider#selectCount(MappedStatement)
     */
    public String selectCountByModel(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        String sql = SqlUtils.selectCount(entityClass) + SqlUtils.fromTable(entityClass, tableName(entityClass)) +
                SqlUtils.whereAllIfColumns(entityClass, isNotEmpty());
        return sql;
    }

    /**
     * @see ConditionProvider#selectCountByCondition(MappedStatement)
     */
    public String selectCountByCondition(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        if (isCheckExampleEntityClass()) {
            sql.append(SqlUtils.exampleCheck(entityClass));
        }
        sql.append(SqlUtils.selectCount(entityClass));
        sql.append(SqlUtils.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlUtils.exampleWhereClause());
        sql.append(SqlUtils.exampleForUpdate());
        return sql.toString();
    }

}
