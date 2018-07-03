package com.spldeolin.beginningmind.core.api.dto;

import org.apache.commons.lang3.math.NumberUtils;
import com.github.pagehelper.PageHelper;
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

    private static final int DEFAULT_PAGE_NO = 1;

    private static final int DEFAULT_PAGE_SIZE = 10;

    private String pageNo;

    private String pageSize;

    public void startPage() {
        int pageNoEx;
        if (NumberUtils.isCreatable(pageNo)) {
            pageNoEx = NumberUtils.toInt(pageNo);
            pageNoEx = pageNoEx > 0 ? pageNoEx : DEFAULT_PAGE_NO;
        } else {
            pageNoEx = DEFAULT_PAGE_NO;
        }
        int pageSizeEx;
        if (NumberUtils.isCreatable(pageSize)) {
            pageSizeEx = NumberUtils.toInt(pageSize);
            pageSizeEx = pageSizeEx > 0 ? pageSizeEx : DEFAULT_PAGE_SIZE;
        } else {
            pageSizeEx = DEFAULT_PAGE_SIZE;
        }
        PageHelper.startPage(pageNoEx, pageSizeEx);
    }

}