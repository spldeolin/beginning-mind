package com.spldeolin.beginningmind.api.mapper;

import org.apache.ibatis.annotations.UpdateProvider;
import com.spldeolin.beginningmind.api.mapper.provider.UpdateMapperProvider;

public interface UpdateMapper<M> {

    @UpdateProvider(type = UpdateMapperProvider.class, method = "dynamicSQL")
    int updateByIdSelective(M model);

}
