package com.spldeolin.beginningmind.api.dto;

import java.io.Serializable;
import java.util.List;
import com.github.pagehelper.PageInfo;
import lombok.Data;

/**
 * 分页包装对象
 *
 * @param <T>
 */
@Data
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageNo;

    private Boolean hasPreviousPage;

    private Boolean hasNextPage;

    private List<T> entitiesInPage;

    private Integer pagesSize;

    private Page() {}

    public static <T> Page<T> wrap(List<T> entities) {
        PageInfo<T> pageInfo = new PageInfo<>(entities);
        Page<T> page = new Page<>();
        page.pageNo = pageInfo.getPageNum();
        page.hasPreviousPage = pageInfo.isHasPreviousPage();
        page.hasNextPage = pageInfo.isHasNextPage();
        page.entitiesInPage = pageInfo.getList();
        page.pagesSize = pageInfo.getPages();
        return page;
    }

}