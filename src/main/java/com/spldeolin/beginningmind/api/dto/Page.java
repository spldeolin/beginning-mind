package com.spldeolin.beginningmind.api.dto;

import java.io.Serializable;
import java.util.List;
import com.github.pagehelper.PageInfo;
import lombok.Data;

/**
 * 分页包装对象
 */
@Data
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 是否有上一页
     */
    private Boolean hasPreviousPage;

    /**
     * 是否有下一页
     */
    private Boolean hasNextPage;

    /**
     * 本页的条目一览
     */
    private List<T> entitiesInPage;

    /**
     * 一共有几页
     */
    private Integer pagesCount;

    private Page() {}

    /**
     * 空页
     */
    public static <T> Page<T> empty() {
        return new Page<>();
    }

    public static <T> Page<T> wrap(List<T> entities) {
        PageInfo<T> pageInfo = new PageInfo<>(entities);
        Page<T> page = new Page<>();
        page.pageNo = pageInfo.getPageNum();
        page.hasPreviousPage = pageInfo.isHasPreviousPage();
        page.hasNextPage = pageInfo.isHasNextPage();
        page.entitiesInPage = pageInfo.getList();
        page.pagesCount = pageInfo.getPages();
        return page;
    }

    public static <T> Page<T> overWriteEntities(Page page, List<T> entities) {
        Page<T> newPage = new Page<>();
        newPage.pageNo = page.pageNo;
        newPage.hasPreviousPage = page.hasPreviousPage;
        newPage.hasNextPage = page.hasNextPage;
        newPage.entitiesInPage = entities;
        newPage.pagesCount = page.pagesCount;
        return newPage;
    }

}