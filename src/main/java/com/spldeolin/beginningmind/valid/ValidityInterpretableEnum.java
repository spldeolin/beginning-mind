package com.spldeolin.beginningmind.valid;

/**
 * 可判断值是否有效的枚举
 *
 * @author Deolin 2019-10-29
 */
public interface ValidityInterpretableEnum {

    /**
     * 判断value对该enum而言是否是有效的枚举值
     */
    boolean isValid(Integer value);

}
