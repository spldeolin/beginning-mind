package com.spldeolin.beginningmind.api.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;
import com.spldeolin.beginningmind.util.SqlUtils;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.provider.base.BaseDeleteProvider;

public class PhysicallyDeleteMapperProvider extends MapperTemplate {

    public PhysicallyDeleteMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * @see BaseDeleteProvider#deleteByPrimaryKey(MappedStatement)
     */
    public String physicallyDelete(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        String sql = SqlUtils.deleteFromTable(entityClass, tableName(entityClass));
        sql += SqlUtils.wherePKColumns(entityClass, false); // 不使用乐观锁
        return sql;
    }

}
