package com.spldeolin.beginningmind.api.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;
import com.spldeolin.beginningmind.util.SqlUtil;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.provider.base.BaseUpdateProvider;

public class UpdateMapperProvider extends MapperTemplate {

    public UpdateMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * @see BaseUpdateProvider#updateByPrimaryKeySelective(MappedStatement)
     */
    public String updateByIdSelective(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        String sql = SqlUtil.updateTable(entityClass, tableName(entityClass)) +
                SqlUtil.updateSetColumns(entityClass, null, true, isNotEmpty()) +
                SqlUtil.wherePKColumns(entityClass, true);
        return sql;
    }


}
