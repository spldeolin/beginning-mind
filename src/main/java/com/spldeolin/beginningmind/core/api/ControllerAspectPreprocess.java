package com.spldeolin.beginningmind.core.api;

/**
 * 该接口实现类的对象，将会在ControllerAspect中，被调用该接口声明的每一个方法，用于预处理字段。
 *
 * @author Deolin
 */
public interface ControllerAspectPreprocess {

    /**
     * 将为null的String字段转化为空字符串，去除对象中String字段前后的空格
     */
    void nullToEmptyAndTrim();

}
