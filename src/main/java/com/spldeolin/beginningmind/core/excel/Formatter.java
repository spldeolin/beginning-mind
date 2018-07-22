package com.spldeolin.beginningmind.core.excel;

/**
 * 格式化器
 * <pre>
 * 定义了目标类与String之间互相转化的策略。
 * 通过自定义该接口的派生类，注册到<code>@ExcelColumn</code>注解中，
 * 实现Excel单元格内容与实体类属性的互相转化。
 * </pre>
 */
public interface Formatter<T> {

    String format(T t) throws Exception;

    T parse(String string) throws Exception;

}
