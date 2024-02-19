package com.spldeolin.beginningmind.allison1875;

import java.util.List;
import com.google.common.collect.Lists;
import com.spldeolin.allison1875.common.Allison1875;
import com.spldeolin.allison1875.common.enums.FileExistenceResolutionEnum;
import com.spldeolin.allison1875.persistencegenerator.PersistenceGeneratorConfig;
import com.spldeolin.allison1875.persistencegenerator.PersistenceGeneratorModule;
import com.spldeolin.allison1875.persistencegenerator.javabean.InformationSchemaDto;
import com.spldeolin.allison1875.persistencegenerator.service.CommentService;
import com.spldeolin.allison1875.persistencegenerator.service.JdbcTypeService;
import com.spldeolin.allison1875.persistencegenerator.service.TableStructureAnalyzerService;
import com.spldeolin.allison1875.persistencegenerator.service.impl.TableStructureAnalyzerServiceImpl;
import com.spldeolin.beginningmind.Application;
import com.spldeolin.beginningmind.allison1875.serviceimpl.persistencegenerator.CommentServiceImpl2;
import com.spldeolin.beginningmind.allison1875.serviceimpl.persistencegenerator.JdbcTypeServiceImpl2;
import com.spldeolin.beginningmind.util.JsonUtils;

/**
 * @author Deolin 2020-11-17
 */
public class PersistenceGeneratorBoot {

    public static void main(String[] args) {
        PersistenceGeneratorConfig config = new PersistenceGeneratorConfig();
        config.setCommonConfig(Constant.COMMON_CONFIG);
        config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306");
        config.setUserName("root");
        config.setPassword("root");
        config.setSchema("beginning_mind");
        config.setTables(Lists.newArrayList("table_naming"));
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
                bind(JdbcTypeService.class).toInstance(new JdbcTypeServiceImpl2("com.spldeolin.beginningmind.enums"));
                bind(CommentService.class).toInstance(new CommentServiceImpl2());
                bind(TableStructureAnalyzerService.class).toInstance(new TableStructureAnalyzerServiceImpl() {
                    @Override
                    protected List<InformationSchemaDto> queryInformationSchema() {
                        // TODO 测试用例表结构
                        return JsonUtils.toListOfObject(
                                "[{\"tableName\":\"table_naming\",\"tableComment\":\"表名\",\"columnName\":\"id\","
                                        + "\"dataType\":\"bigint\",\"columnType\":\"bigint(20)\","
                                        + "\"columnComment\":\"主键\",\"columnKey\":\"PRI\","
                                        + "\"characterMaximumLength\":null,\"isNullable\":\"NO\","
                                        + "\"columnDefault\":null},"
                                        + "{\"tableName\":\"table_naming\",\"tableComment\":\"表名\","
                                        + "\"columnName\":\"aaaaa\",\"dataType\":\"varchar\",\"columnType\":\"varchar"
                                        + "(25)"
                                        + "\",\"columnComment\":\"可穷举的字段 E(q=1 w=2 e=3 r=4)\",\"columnKey\":\"\","
                                        + "\"characterMaximumLength\":25,\"isNullable\":\"NO\",\"columnDefault\":null},"
                                        + "{\"tableName\":\"table_naming\",\"tableComment\":\"表名\","
                                        + "\"columnName\":\"hidden_memo\",\"dataType\":\"varchar\","
                                        + "\"columnType\":\"varchar(255)\",\"columnComment\":\"备注，对项目隐藏，仅在数据库中可见\","
                                        + "\"columnKey\":\"\",\"characterMaximumLength\":255,\"isNullable\":\"NO\","
                                        + "\"columnDefault\":\"\"},{\"tableName\":\"table_naming\","
                                        + "\"tableComment\":\"表名\",\"columnName\":\"create_staff_id\","
                                        + "\"dataType\":\"bigint\",\"columnType\":\"bigint(20)\","
                                        + "\"columnComment\":\"插入数据时登录者的staffId，若数据并非登录者插入的，值为-1\",\"columnKey\":\"\","
                                        + "\"characterMaximumLength\":null,\"isNullable\":\"NO\","
                                        + "\"columnDefault\":\"-1\"},{\"tableName\":\"table_naming\","
                                        + "\"tableComment\":\"表名\",\"columnName\":\"create_time\","
                                        + "\"dataType\":\"datetime\",\"columnType\":\"datetime\","
                                        + "\"columnComment\":\"插入数据的时间\",\"columnKey\":\"\","
                                        + "\"characterMaximumLength\":null,\"isNullable\":\"NO\","
                                        + "\"columnDefault\":\"CURRENT_TIMESTAMP\"},{\"tableName\":\"table_naming\","
                                        + "\"tableComment\":\"表名\",\"columnName\":\"update_staff_id\","
                                        + "\"dataType\":\"bigint\",\"columnType\":\"bigint(20)\","
                                        + "\"columnComment\":\"最近一次更新数据时登录者的staffId，若数据从未更新过，值与create_staff_id"
                                        + "保持一致，若数据并非登录者更新的，值为-1\",\"columnKey\":\"\",\"characterMaximumLength\":null,"
                                        + "\"isNullable\":\"NO\",\"columnDefault\":\"-1\"},"
                                        + "{\"tableName\":\"table_naming\",\"tableComment\":\"表名\","
                                        + "\"columnName\":\"update_time\",\"dataType\":\"datetime\","
                                        + "\"columnType\":\"datetime\","
                                        + "\"columnComment\":\"最近一次更新数据的时间。如果数据从未更新过，与create_time保持一致\","
                                        + "\"columnKey\":\"\",\"characterMaximumLength\":null,\"isNullable\":\"NO\","
                                        + "\"columnDefault\":\"CURRENT_TIMESTAMP\"}]", InformationSchemaDto.class);
                    }
                });
            }
        });
    }

}