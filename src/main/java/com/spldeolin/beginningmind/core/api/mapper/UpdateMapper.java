package com.spldeolin.beginningmind.core.api.mapper;

import org.apache.ibatis.annotations.UpdateProvider;
import com.spldeolin.beginningmind.core.api.mapper.provider.UpdateMapperProvider;

/**
 * @author Deolin
 */
public interface UpdateMapper<M> {

    @UpdateProvider(type = UpdateMapperProvider.class, method = "dynamicSQL")
    int updateByIdSelective(M model);

}
