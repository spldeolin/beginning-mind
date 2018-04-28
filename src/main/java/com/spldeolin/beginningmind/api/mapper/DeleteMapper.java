package com.spldeolin.beginningmind.api.mapper;

import org.apache.ibatis.annotations.UpdateProvider;
import com.spldeolin.beginningmind.api.mapper.provider.DeleteMapperProvider;

public interface DeleteMapper<M> extends PhysicallyDeleteMapper {

    @UpdateProvider(type = DeleteMapperProvider.class, method = "dynamicSQL")
    int deleteById(Long id);

    /**
     * @param ids e.g.: 1, 2, 3, 4, 5, 6
     */
    @UpdateProvider(type = DeleteMapperProvider.class, method = "dynamicSQL")
    int deleteBatchByIds(String ids);

}