package com.spldeolin.beginningmind.allison1875;

import com.google.common.collect.Lists;
import com.spldeolin.allison1875.base.Allison1875;
import com.spldeolin.allison1875.persistencegenerator.PersistenceGeneratorConfig;
import com.spldeolin.beginningmind.allison1875.config.ConfigConstant;
import com.spldeolin.beginningmind.allison1875.config.EnumConfig;
import com.spldeolin.beginningmind.allison1875.module.BmPersistenceGeneratorModule;

/**
 * @author Deolin 2020-11-17
 */
public class PersistenceGeneratorBoot {

    public static void main(String[] args) {
        PersistenceGeneratorConfig config = ConfigConstant.persistenceGeneratorConfig;
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306");
        config.setUserName("root");
        config.setPassword("root");
        config.setAuthor("Deolin");
        config.setSchema("beginning_mind");
        config.setTables(Lists.newArrayList());
        config.setMapperXmlDirectoryPath("src/main/resources/mapper");
        config.batchSetAllPackagesByWildcard("com.spldeolin.beginningmind.-");
        config.setEnableGenerateQueryDesign(false);
        config.setIsEntityEndWithEntity(true);
        EnumConfig enumConfig = new EnumConfig();
        enumConfig.setEnumPackage("com.spldeolin.beginningmind.enums");
        Allison1875.allison1875(PersistenceGeneratorBoot.class, new BmPersistenceGeneratorModule(),
                ConfigConstant.baseConfig, config, enumConfig);
    }

}