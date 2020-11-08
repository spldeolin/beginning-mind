package com.spldeolin.beginningmind.allison1875;

import com.google.common.collect.Lists;
import com.spldeolin.allison1875.base.Allison1875;
import com.spldeolin.allison1875.persistencegenerator.PersistenceGeneratorConfig;
import com.spldeolin.allison1875.persistencegenerator.processor.PersistenceGenerator;

/**
 * @author Deolin 2020-10-16
 */
public class PersistenceGeneratorBoot {

    public static void main(String[] args) {
        Allison1875.allison1875(PersistenceGeneratorBoot.class, new PersistenceGenerator());
    }

    static {
        PersistenceGeneratorConfig conf = PersistenceGeneratorConfig.getInstance();

        conf.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/beginning_mind");

        conf.setUserName("root");

        conf.setPassword("root");

        conf.setAuthor("Deolin");

        conf.setSchema("beginning_mind");

        conf.setTables(Lists.newArrayList("user2permission"));

        conf.setMapperXmlDirectoryPath("src/main/resources/mapper");

        conf.setMapperPackage("com.spldeolin.beginningmind.mapper");

        conf.setEntityPackage("com.spldeolin.beginningmind.entity");

        conf.setEnableGenerateQueryDesign(true);

        conf.setQueryPredicateQualifier("com.spldeolin.beginningmind.support.QueryPredicate");

        conf.setQueryDesignPackage("com.spldeolin.beginningmind.querydesign");

        conf.setIsEntityUsingAlias(false);

        conf.setIsEntityEndWithEntity(true);

        conf.setDeletedSql("delete_flag = TRUE");

        conf.setNotDeletedSql("delete_flag = FALSE");

        conf.setHiddenColumns(Lists.newArrayList("hidden_memo"));

        conf.setNotKeyColumns(Lists.newArrayList());

        conf.setSuperEntityQualifier("com.spldeolin.beginningmind.ancestor.EntityAncestor");

        conf.setAlreadyInSuperEntity(Lists.newArrayList("id", "inserted_at", "updated_at"));
    }

}