package com.spldeolin.beginningmind.api.mapper.provider;

import java.util.Set;
import org.apache.ibatis.mapping.MappedStatement;
import com.spldeolin.beginningmind.util.SqlUtil;
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
        String sql = SqlUtil.selectAllColumns(entityClass) +
                SqlUtil.fromTable(entityClass, tableName(entityClass)) +
                SqlUtil.wherePKColumns(entityClass);
        return sql;
    }

    /**
     * @see IdsProvider#selectByIds(MappedStatement)
     */
    public String selectBatchByIds(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlUtil.selectAllColumns(entityClass));
        sql.append(SqlUtil.fromTable(entityClass, tableName(entityClass)));
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
        String sql = SqlUtil.selectAllColumns(entityClass) +
                SqlUtil.fromTable(entityClass, tableName(entityClass)) +
                " where " + "is_deleted" + "=FALSE " +
                SqlUtil.orderByDefault(entityClass);
        return sql;
    }

    /**
     * @see BaseSelectProvider#selectOne(MappedStatement)
     */
    public String selectBatchByModel(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        setResultType(ms, entityClass);
        String sql = SqlUtil.selectAllColumns(entityClass) +
                SqlUtil.fromTable(entityClass, tableName(entityClass)) +
                SqlUtil.whereAllIfColumns(entityClass, isNotEmpty());
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
            sql.append(SqlUtil.exampleCheck(entityClass));
        }
        sql.append("<if test=\"distinct\">distinct</if>");
        sql.append(SqlUtil.exampleSelectColumns(entityClass));
        sql.append(SqlUtil.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlUtil.exampleWhereClause());
        sql.append(SqlUtil.exampleOrderBy(entityClass));
        sql.append(SqlUtil.exampleForUpdate());
        return sql.toString();
    }

    /**
     * @see BaseSelectProvider#selectCount(MappedStatement)
     */
    public String selectCountByModel(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        String sql = SqlUtil.selectCount(entityClass) + SqlUtil.fromTable(entityClass, tableName(entityClass)) +
                SqlUtil.whereAllIfColumns(entityClass, isNotEmpty());
        return sql;
    }

    /**
     * @see ConditionProvider#selectCountByCondition(MappedStatement)
     */
    public String selectCountByCondition(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        if (isCheckExampleEntityClass()) {
            sql.append(SqlUtil.exampleCheck(entityClass));
        }
        sql.append(SqlUtil.selectCount(entityClass));
        sql.append(SqlUtil.fromTable(entityClass, tableName(entityClass)));
        sql.append(SqlUtil.exampleWhereClause());
        sql.append(SqlUtil.exampleForUpdate());
        return sql.toString();
    }

}
