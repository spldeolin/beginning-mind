package com.spldeolin.beginningmind.service.allison1875;

import com.google.common.collect.Lists;
import com.spldeolin.allison1875.common.config.CommonConfig;
import com.spldeolin.beginningmind.service.Application;

/**
 * @author Deolin 2024-02-19
 */
public interface Constant {

    String BASE_PACKAGE = Application.class.getPackage().getName();

    CommonConfig COMMON_CONFIG = new CommonConfig().setReqDtoPackage(BASE_PACKAGE + ".javabean.req")
            .setRespDtoPackage(BASE_PACKAGE + ".javabean.resp").setServicePackage(BASE_PACKAGE + ".service")
            .setServiceImplPackage(BASE_PACKAGE + ".service.impl").setMapperPackage(BASE_PACKAGE + ".mapper")
            .setEntityPackage(BASE_PACKAGE + ".entity").setDesignPackage(BASE_PACKAGE + ".design")
            .setCondPackage(BASE_PACKAGE + ".javabean.cond").setRecordPackage(BASE_PACKAGE + ".javabean.record")
            .setWholeDtoPackage(BASE_PACKAGE + ".javabean")
            .setMapperXmlDirectoryPaths(Lists.newArrayList("src/main/resources/mapper")).setAuthor("Deolin")
            .setIsJavabeanCloneable(false).setIsJavabeanSerializable(false);

}