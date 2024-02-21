package com.spldeolin.beginningmind.app.allison1875;

import java.io.IOException;
import com.google.common.collect.Lists;
import com.spldeolin.allison1875.common.Allison1875;
import com.spldeolin.allison1875.common.enums.FileExistenceResolutionEnum;
import com.spldeolin.allison1875.persistencegenerator.PersistenceGeneratorConfig;
import com.spldeolin.allison1875.persistencegenerator.PersistenceGeneratorModule;
import com.spldeolin.allison1875.persistencegenerator.service.CommentService;
import com.spldeolin.allison1875.persistencegenerator.service.JdbcTypeService;
import com.spldeolin.beginningmind.app.Application;
import com.spldeolin.beginningmind.app.allison1875.serviceimpl.persistencegenerator.CommentServiceImpl2;
import com.spldeolin.beginningmind.app.allison1875.serviceimpl.persistencegenerator.JdbcTypeServiceImpl2;

/**
 * @author Deolin 2020-11-17
 */
public class PersistenceGeneratorBoot {

    public static void main(String[] args) throws IOException {
        PersistenceGeneratorConfig config = new PersistenceGeneratorConfig();
        config.setCommonConfig(Constant.COMMON_CONFIG);
        config.setJdbcUrl("jdbc:mysql://localhost:3306");
        config.setUserName("root");
        config.setPassword("root");
        config.setSchema("beginningmind");
        config.setTables(Lists.newArrayList());
        config.setEnableGenerateDesign(true);
        config.setIsEntityUsingAlias(false);
        config.setIsEntityEndWithEntity(true);
        config.setDeletedSql(null);
        config.setNotDeletedSql(null);
        config.setHiddenColumns(Lists.newArrayList());
        config.setNotKeyColumns(Lists.newArrayList());
        config.setSuperEntityQualifier(null);
        config.setAlreadyInSuperEntity(Lists.newArrayList());
        config.setEnableNoModifyAnnounce(true);
        config.setEnableLotNoAnnounce(false);
        config.setEnableEntityImplementSerializable(true);
        config.setEnableEntityImplementCloneable(true);
        config.setEntityExistenceResolution(FileExistenceResolutionEnum.OVERWRITE);
        config.setDisableInsert(false);
        config.setDisableBatchInsert(false);
        config.setDisableBatchInsertEvenNull(false);
        config.setDisableBatchUpdate(false);
        config.setDisableBatchUpdateEvenNull(false);
        config.setDisableQueryById(false);
        config.setDisableUpdateById(false);
        config.setDisableUpdateByIdEvenNull(false);
        config.setDisableQueryByIds(false);
        config.setDisableQueryByIdsEachId(false);
        config.setDisableQueryByKey(false);
        config.setDisableDeleteByKey(false);
        config.setDisableQueryByKeys(false);
        config.setDisableQueryByEntity(false);
        config.setDisableListAll(false);
        config.setDisableInsertOrUpdate(false);

        Allison1875.allison1875(Application.class, new PersistenceGeneratorModule(config) {
            @Override
            protected void configure() {
                super.configure();
                bind(JdbcTypeService.class).toInstance(
                        new JdbcTypeServiceImpl2("com.spldeolin.beginningmind.app.enums"));
                bind(CommentService.class).toInstance(new CommentServiceImpl2());
            }
        });
    }

}