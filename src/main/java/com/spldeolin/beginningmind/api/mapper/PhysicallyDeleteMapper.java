package com.spldeolin.beginningmind.api.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import com.spldeolin.beginningmind.api.mapper.provider.PhysicallyDeleteMapperProvider;

public interface PhysicallyDeleteMapper {

    /**
     * <pre>
     * ############
     * #          #
     * #  DANGER  #
     * #          #
     * ############
     * </pre>
     */
    @DeleteProvider(type = PhysicallyDeleteMapperProvider.class, method = "dynamicSQL")
    int physicallyDelete(Long id);

}