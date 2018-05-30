package com.spldeolin.beginningmind.core.api.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import com.spldeolin.beginningmind.core.api.mapper.provider.PhysicallyDeleteMapperProvider;

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