package com.spldeolin.beginningmind.core.api.mapper;

import java.util.List;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import com.spldeolin.beginningmind.core.api.mapper.provider.InsertMapperProvider;

public interface InsertMapper<M> {

    @Options(useGeneratedKeys = true)
    @InsertProvider(type = InsertMapperProvider.class, method = "dynamicSQL")
    int insert(M Model);

    @Options(useGeneratedKeys = true)
    @InsertProvider(type = InsertMapperProvider.class, method = "dynamicSQL")
    int insertBatch(List<M> model);

}
