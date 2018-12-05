package com.spldeolin.beginningmind.core.api.dto;

import org.apache.commons.lang3.math.NumberUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * 分页参数实体类
 * <pre>
 * 声明了页码和每页条目数两个字段
 * 可用于绑定url中的&pageNo=&pageSize=参数
 * PageParam.startPage与PageHelper.startPage(pageNo, pageSize)等价
 * </pre>
 *
 * @author Deolin 2018/06/28
 */
@Data
public class PageParam {

    private static final long DEFAULT_PAGE_NO = 1;

    private static final long DEFAULT_PAGE_SIZE = 10;

    private String pageNo;

    private String pageSize;

    public <T> Page<T> build() {
        long rationalPageNo = retionalise(pageNo, DEFAULT_PAGE_NO);
        long rationalPageSize = retionalise(pageSize, DEFAULT_PAGE_SIZE);
        Page<T> mybatisPlusPageParam = new Page<>();
        mybatisPlusPageParam.setCurrent(rationalPageNo).setSize(rationalPageSize);
        return mybatisPlusPageParam;
    }

    private long retionalise(String rawNumber, long defaultNumber) {
        long result;
        if (NumberUtils.isCreatable(rawNumber)) {
            result = NumberUtils.toLong(rawNumber);
            result = result > 0 ? result : defaultNumber;
        } else {
            result = defaultNumber;
        }
        return result;
    }

}