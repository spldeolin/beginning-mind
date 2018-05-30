package com.spldeolin.beginningmind.core.constant;

import org.apache.commons.codec.digest.DigestUtils;

public class CoupledConstant {

    /**
     * 这个常量必须包含了所有swagger请求URL的开头片段
     */
    public static final String[] SWAGGER_URL_PREFIXES = {"/swagger", "/webjars/", "/v2/"};

    /**
     * 必须与log4j2-*.yml的PatternLayout.pattern中的%X{username}占位符相同
     */
    public static final String LOG_PATTERN_PARAM = "username";

    /**
     * SHA512一次的默认密码（000000）
     */
    public static final String DEFAULT_PASSWORD_EX = DigestUtils.sha512Hex("000000");

}