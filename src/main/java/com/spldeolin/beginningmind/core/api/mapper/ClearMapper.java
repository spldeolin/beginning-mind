package com.spldeolin.beginningmind.core.api.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import com.spldeolin.beginningmind.core.api.mapper.provider.ClearProvider;

/**
 * @author Deolin 2018/09/20
 */
public interface ClearMapper<M> {

    @UpdateProvider(type = ClearProvider.class, method = "dynamicSQL")
    int clearProperty(@Param("id") Long id, @Param("propertyName") String propertyName);

}
