package com.spldeolin.beginningmind.constant;

public class CoupledConstant {

    /**
     * 这个常量必须与生产环境的application-${p}.yml中的${p}一致
     */
    public static final String PROD_PROFILE_NAME = "prod";

    /**
     * 这个常量必须包含了所有swagger请求URL的开头片段
     */
    public static final String[] SWAGGER_URL_MATCHING_PREFIXES = {"/swagger", "/webjars/", "/v2/"};

    /**
     * 代表Spring Boot Web提供的错误页面的全URL
     */
    public static final String ERROR_PAGE_URL = "/error";

    /**
     * 必须与log4j2-*.yml的PatternLayout.pattern中的%X{username}占位符相同
     */
    public static final String LOG_PATTERN_PARAM = "username";

}