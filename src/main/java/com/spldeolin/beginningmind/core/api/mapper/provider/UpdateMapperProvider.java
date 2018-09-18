package com.spldeolin.beginningmind.core.api.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;
import com.spldeolin.beginningmind.core.api.mapper.util.SqlUtils;
import com.spldeolin.beginningmind.core.api.mapper.util.StringCompressUtils;
import lombok.extern.log4j.Log4j2;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.provider.base.BaseUpdateProvider;

/**
 * @author Deolin
 */
@Log4j2
public class UpdateMapperProvider extends MapperTemplate {

    public UpdateMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * @see BaseUpdateProvider#updateByPrimaryKeySelective(MappedStatement)
     */
    public String updateByIdSelective(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        String sql = SqlUtils.updateTable(entityClass, tableName(entityClass)) +
                SqlUtils.updateSetColumns(entityClass, null, true, isNotEmpty()) +
                SqlUtils.wherePKColumns(entityClass, true);
        log.debug("Provide Mapper Statement: " + StringCompressUtils.trimUnnecessaryBlanks(sql));
        return sql;
    }

}
