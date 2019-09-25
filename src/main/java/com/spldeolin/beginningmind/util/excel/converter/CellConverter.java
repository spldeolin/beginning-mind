package com.spldeolin.beginningmind.util.excel.converter;

import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.util.excel.exception.CellConverterReadException;

/**
 * 格式化器
 * <pre>
 * 定义了目标类与String之间互相转换的策略。
 * 通过自定义该接口的派生类，注册到<code>@ExcelColumn</code>注解中，
 * 实现Excel单元格内容与实体类属性的互相转换。
 * </pre>
 *
 * @author Deolin
 */
public interface CellConverter<T> {

    /**
     * 通过实现这个方法，以提供对象转换为单元格内容的策略
     *
     * @param t 待转换对象，Excels调用这个方法时参数不会为null
     * @return 转换后的String
     */
    String writeToCellContent(@Nonnull T t);

    /**
     * 通过实现这个方法，以提供单元格内容转换为T对象的策略
     *
     * @param string 待转换String，Excels调用这个方法时参数不会为null
     * @return 转化后的T对象
     * @throws CellConverterReadException 如果无法转换，应该抛出这个异常
     */
    T readFromCellContent(@Nonnull String string) throws CellConverterReadException;

}
