package com.spldeolin.beginningmind.allison1875;

import com.google.common.collect.Lists;
import com.spldeolin.allison1875.common.config.CommonConfig;

/**
 * @author Deolin 2024-02-19
 */
public interface Constant {

    CommonConfig COMMON_CONFIG = new CommonConfig().setReqDtoPackage("com.spldeolin.beginningmind.javabean.req")
            .setRespDtoPackage("com.spldeolin.beginningmind.javabean.resp")
            .setServicePackage("com.spldeolin.beginningmind.service")
            .setServiceImplPackage("com.spldeolin.beginningmind.serviceimpl")
            .setMapperPackage("com.spldeolin.beginningmind.mapper")
            .setEntityPackage("com.spldeolin.beginningmind.entity")
            .setDesignPackage("com.spldeolin.beginningmind.design")
            .setCondPackage("com.spldeolin.beginningmind.javabean.cond")
            .setRecordPackage("com.spldeolin.beginningmind.javabean.record")
            .setWholeDtoPackage("com.spldeolin.beginningmind.javabean")
            .setMapperXmlDirectoryPaths(Lists.newArrayList("src/main/resources/mapper")).setAuthor("Deolin");

}