package com.spldeolin.beginningmind.core.constant;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Deolin
 */
public class CoupledConstant {

    /**
     * 这个常量必须包含了所有swagger请求URL的开头片段
     */
    public static final String[] SWAGGER_URL_PREFIXES = {"/swagger", "/webjars/", "/v2/"};

    /**
     * 必须与log4j2-*.yml的PatternLayout.pattern中的%X{insignia}占位符名相同
     */
    public static final String LOG_MDC_INSIGNIA = "insignia";

    /**
     * SHA512一次的默认密码（0）
     */
    public static final String DEFAULT_PASSWORD_EX = DigestUtils.sha512Hex("0");

    /**
     * 最高层组织架构的parentId
     */
    public static final Long ROOT_ORGANIZATION_ID = 0L;

    /**
     * 最高层组织架构的parentId
     */
    public static final String ROOT_ORGANIZATION_ID_S = "0";

    /**
     * 第一个组织架构
     */
    public static final Long EDEN_ORGANIZATION_ID = 1L;

    /**
     * 第一个用户
     */
    public static final Long ADAM_USER_ID = 1L;

}