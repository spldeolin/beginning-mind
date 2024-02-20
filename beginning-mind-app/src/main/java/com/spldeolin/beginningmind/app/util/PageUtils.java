package com.spldeolin.beginningmind.app.util;

import java.util.List;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * @author Deolin 2024-02-20
 */
public class PageUtils {

    public static <T> PageInfo<T> transferType(List<?> records, List<T> transfered) {
        PageInfo<?> recordPage = new PageInfo<>(records);

        Page<T> page = new Page<>(recordPage.getPageNum(), recordPage.getPageSize());
        page.setTotal(recordPage.getTotal());
        PageInfo<T> result = PageInfo.of(page);
        result.setList(transfered);
        return result;
    }

}