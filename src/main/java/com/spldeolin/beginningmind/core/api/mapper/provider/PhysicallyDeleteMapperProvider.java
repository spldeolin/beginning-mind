package com.spldeolin.beginningmind.core.api.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;
import com.spldeolin.beginningmind.core.api.mapper.util.SqlUtils;
import com.spldeolin.beginningmind.core.api.mapper.util.StringCompressUtils;
import lombok.extern.log4j.Log4j2;
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
    public String physicallyDelete(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        String sql = SqlUtils.deleteFromTable(entityClass, tableName(entityClass));
        sql += SqlUtils.wherePKColumns(entityClass, false); // 不使用乐观锁
        log.debug("Provide Mapper Statement: " + StringCompressUtils.trimUnnecessaryBlanks(sql));
        return sql;
    }

}
