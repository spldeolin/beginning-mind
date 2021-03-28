package com.spldeolin.beginningmind.allison1875.handle.docanalyzer;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.inject.Singleton;
import com.spldeolin.allison1875.docanalyzer.handle.SpecificFieldDescriptionsHandle;

/**
 * @author Deolin 2020-11-30
 */
@Singleton
public class PageHelperDescriptionsHandle implements SpecificFieldDescriptionsHandle {

    @Override
    public Table<String, String, String> provideSpecificFieldDescriptions() {
        Table<String, String, String> extra = HashBasedTable.create();
        extra.put(PageSerializable.class.getName(), "total", "总记录数");
        extra.put(PageSerializable.class.getName(), "list", "结果集");
        extra.put(PageInfo.class.getName(), "pageNum", "当前页");
        extra.put(PageInfo.class.getName(), "pageSize", "每页的数量");
        extra.put(PageInfo.class.getName(), "size", "当前页的数量");
        extra.put(PageInfo.class.getName(), "startRow", "当前页面第一个元素在数据库中的行号");
        extra.put(PageInfo.class.getName(), "endRow", "当前页面最后一个元素在数据库中的行号");
        extra.put(PageInfo.class.getName(), "pages", "总页数");
        extra.put(PageInfo.class.getName(), "prePage", "前一页");
        extra.put(PageInfo.class.getName(), "nextPage", "下一页");
        extra.put(PageInfo.class.getName(), "isFirstPage", "是否为第一页");
        extra.put(PageInfo.class.getName(), "isLastPage", "是否为最后一页");
        extra.put(PageInfo.class.getName(), "hasPreviousPage", "是否有前一页");
        extra.put(PageInfo.class.getName(), "hasNextPage", "是否有下一页");
        extra.put(PageInfo.class.getName(), "navigatePages", "导航页码数");
        extra.put(PageInfo.class.getName(), "navigatepageNums", "所有导航页号");
        extra.put(PageInfo.class.getName(), "navigateFirstPage", "导航条上的第一页");
        extra.put(PageInfo.class.getName(), "navigateLastPage", "导航条上的最后一页");
        return extra;
    }

}