package com.spldeolin.beginningmind.ancestor;

import javax.annotation.Nullable;

/**
 * 所有标准错误的直接或间接父类
 *
 * @author Deolin 2021-08-17
 */
public interface StderrAncestor {

    /**
     * 错误码
     * 需要NOT-BLANK，只建议使用英文与数字的组合，并确保全系统唯一
     */
    String code();

    /**
     * 对错误的描述
     * 当派生类作为new StdException()的参数时，彻底抛出异常后，errorMessage将会显示在前端页面上
     */
    @Nullable
    String errorMessage();

}
