package com.spldeolin.beginningmind.valid;

/**
 * 这个接口的派生类需要实现对枚举值的是否有效的校验
 *
 * @author Deolin 2019-10-29
 */
public interface ValidityInterpretable {

    /**
     * 判断value对该enum而言是否是有效的枚举值
     */
    boolean isValid(Integer value);

}
