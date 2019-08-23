package com.spldeolin.beginningmind.core.util.excel.formatter;

import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.excel.exception.ConverterReadException;

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
public interface Converter<T> {

    /**
     * 通过实现这个方法，以提供T对象转换为String的策略
     *
     * @param t 待转换对象，Excels调用这个方法时参数不会为null
     * @return 转换后的String
     */
    String write(@Nonnull T t);

    /**
     * 通过实现这个方法，以提供String转换为T对象的策略
     *
     * @param string 待转换String，Excels调用这个方法时参数不会为null
     * @return 转化后的T对象
     * @throws ConverterReadException 如果无法转换，应该抛出这个异常
     */
    T read(@Nonnull String string) throws ConverterReadException;

}
