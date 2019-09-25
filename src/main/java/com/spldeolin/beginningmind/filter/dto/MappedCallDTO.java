package com.spldeolin.beginningmind.filter.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * 请求调用Mapper中方法的信息，在MapperAspect中构建
 *
 * @author Deolin 2018/12/28
 */
@Data
public class MappedCallDTO implements Serializable {

    /**
     * mapper接口名
     */
    private String target;

    /**
     * 耗时
     */
    private Long elapsed;

    private static final long serialVersionUID = 1L;

    public MappedCallDTO(String target, Long elapsed) {
        this.target = target;
        this.elapsed = elapsed;
    }

}
