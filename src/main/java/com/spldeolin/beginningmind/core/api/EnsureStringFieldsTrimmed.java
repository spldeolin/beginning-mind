package com.spldeolin.beginningmind.core.api;

/**
 * 实现了这个接口的Input类，作为请求方法的参数时，
 * 将会被切面调用trimStringFields方法，
 * 用于去除对象中String字段值前后的空格
 *
 * @author Deolin
 */
public interface EnsureStringFieldsTrimmed {

    void trimStringFields();

}
