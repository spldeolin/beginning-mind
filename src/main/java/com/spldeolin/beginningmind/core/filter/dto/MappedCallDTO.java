package com.spldeolin.beginningmind.core.filter.dto;

import java.util.List;
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
     * 参数
     */
    private List<Object> parameters;

    /**
     * 返回值size，如果返回值不是Collection的派生类，那么这个字段为null
     */
    private Integer returnSize;

    /**
     * 耗时
     */
    private Long elapsed;

}
