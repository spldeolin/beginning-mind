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
        PersistenceGeneratorConfig persistenceGeneratorConfig = ConfigConstant.persistenceGeneratorConfig;
        persistenceGeneratorConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306");
        persistenceGeneratorConfig.setUserName("root");
        persistenceGeneratorConfig.setPassword("root");
        persistenceGeneratorConfig.setAuthor("Deolin");
        persistenceGeneratorConfig.setSchema("beginning_mind");
        persistenceGeneratorConfig.setTables(Lists.newArrayList());
        persistenceGeneratorConfig.setMapperXmlDirectoryPath("src/main/resources/mapper");
        persistenceGeneratorConfig.batchSetAllPackagesByWildcard("com.spldeolin.beginningmind.-");
        persistenceGeneratorConfig.setEnableGenerateDesign(true);
        persistenceGeneratorConfig.setIsEntityEndWithEntity(true);
        EnumConfig enumConfig = new EnumConfig();
        enumConfig.setEnumPackage("com.spldeolin.beginningmind.enums");
        Allison1875.allison1875(PersistenceGeneratorBoot.class,
                new BmPersistenceGeneratorModule(persistenceGeneratorConfig, enumConfig));
    }

}