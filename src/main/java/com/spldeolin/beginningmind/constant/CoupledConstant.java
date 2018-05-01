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

}