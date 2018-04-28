package com.spldeolin.beginningmind.config;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * 配置一览
 */
@Component
@ConfigurationProperties(value = "beginning-mind")
@Data
public class BeginningMindProperties {

    /**
     * “一个曲奇”
     */
    private String oneCookie;

    /**
     * HTTP请求 页码的URL参数名
     */
    private String pageNoParamName;

    /**
     * HTTP请求 分页尺寸的URL参数名
     */
    private String pageSizeParamName;

    /**
     * 数据库 数据插入时间的字段名
     */
    private String insertTimeColumnName;

    /**
     * 数据库 数据更新时间的字段名
     */
    private String updateTimeColumnName;

    /**
     * 数据库 数据删除标识的字段名
     */
    private String deleteFlagColumnName;

    /**
     * 数据库 数据删除标识满足什么条件才算数据被删除
     */
    private String HowDeleteIsDelete;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 使用一分钱支付
     */
    private Boolean useOneCentWhenPay;

    /**
     * “时间”格式
     */
    private Time time;

    /**
     * 中文文案
     */
    private TextZhHans textZhHans;

    /**
     * 日语文案
     */
    private TextJaJp textJaJp;

    /**
     * 微信
     */
    private Wechat wechat;

    /**
     * 阿里大鱼
     */
    private Alidayu alidayu;

    /**
     * 各种类型的构造体
     */
    private Struct struct;

    @Data
    public static class Time {

        private String defaultDatePattern;

        private String defaultTimePattern;

        private String defaultDatetimePattern;

        private Boolean serializeJavaUtilDateToTimestamp;

    }

    /**
     * 中文简体文案
     */
    @Data
    public static class TextZhHans {

        private String hello;

        private String excellent;

    }

    /**
     * 日语文案
     */
    @Data
    public static class TextJaJp {

        private String hello;

        private String excellent;

    }

    /**
     * 微信
     */
    @Data
    public static class Wechat {

        private String appid;

        private String appsecret;

    }

    /**
     * 阿里大鱼
     */
    @Data
    public static class Alidayu {

        private String appid;

        private String appsecret;

        private String signName;

        private String templateCode;

    }

    /**
     * 数组/Map性质的构造
     */
    @Data
    public static class Struct {

        /**
         * 简单集合型构造
         */
        private Array array;

        /**
         * Map集合型勾结
         */
        private KeyValueArray keyValueArray;

        @Data
        public static class Array {

            private List<String> strings;

            private List<Integer> integers;

        }

        /**
         * Map列表型构造
         */
        @Data
        public static class KeyValueArray {

            private List<CaloricFood> highCaloricFood;

            private List<CaloricFood> lowCaloricFood;

            @Data
            public static class CaloricFood {

                private String name;

                private BigDecimal calorie;

            }

        }

    }

}