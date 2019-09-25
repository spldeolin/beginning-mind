package com.spldeolin.beginningmind.filter.constant;

/**
 * 过滤器包下所有过滤器的顺序，数字越小越外层
 *
 * @author Deolin 2019-06-20
 */
public interface FilterOrderConstant {

    int WEB_CONTEXT_FILTER_ORDER = 1;

    int LOG_MDC_FILTER_ORDER = 2;

    int READ_CONTENT_FILTER_ORDER = 3;

    int SESSION_REFLASH_FILTER_ORDER = 4;

}
