package com.spldeolin.beginningmind.core.constant;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 系统常量
 *
 * @author Deolin
 */
public interface CoupledConstant {

    /**
     * 必须与log4j2.yml的PatternLayout.pattern中的%X{insignia}占位符名相同
     */
    String LOG_MDC_INSIGNIA = "insignia";

    /**
     * SHA512一次的默认密码（000000）
     */
    String DEFAULT_PASSWORD_EX = DigestUtils.sha512Hex("000000");

    /**
     * 第一个用户
     */
    Long ADAM_USER_ID = 1L;

}