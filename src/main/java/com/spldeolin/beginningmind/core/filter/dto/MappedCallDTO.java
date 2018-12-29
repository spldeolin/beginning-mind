package com.spldeolin.beginningmind.core.filter.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 请求调用Mapper中方法的信息，在MapperAspect中构建
 *
 * @author Deolin 2018/12/28
 */
@Data
@Builder
public class MappedCallDTO {

    /**
     * mapper接口名
     */
    private String target;

    /**
     * 耗时
     */
    private Long elapsed;

}
