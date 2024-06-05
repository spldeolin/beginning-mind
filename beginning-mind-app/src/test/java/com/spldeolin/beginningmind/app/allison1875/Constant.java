package com.spldeolin.beginningmind.app.allison1875;

import com.google.common.collect.Lists;
import com.spldeolin.allison1875.common.config.CommonConfig;

/**
 * @author Deolin 2024-02-19
 */
public interface Constant {

    CommonConfig COMMON_CONFIG = new CommonConfig().setReqDtoPackage("com.spldeolin.beginningmind.app.javabean.req")
            .setRespDtoPackage("com.spldeolin.beginningmind.app.javabean.resp")
            .setServicePackage("com.spldeolin.beginningmind.app.service")
            .setServiceImplPackage("com.spldeolin.beginningmind.app.service.impl")
            .setMapperPackage("com.spldeolin.beginningmind.app.mapper")
            .setEntityPackage("com.spldeolin.beginningmind.app.entity")
            .setDesignPackage("com.spldeolin.beginningmind.app.design")
            .setCondPackage("com.spldeolin.beginningmind.app.javabean.cond")
            .setRecordPackage("com.spldeolin.beginningmind.app.javabean.record")
            .setWholeDtoPackage("com.spldeolin.beginningmind.app.javabean")
            .setMapperXmlDirectoryPaths(Lists.newArrayList("src/main/resources/mapper")).setAuthor("Deolin")
            .setIsJavabeanCloneable(false).setIsJavabeanSerializable(false);

}