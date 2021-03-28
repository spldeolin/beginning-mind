package com.spldeolin.beginningmind.allison1875.config;

import java.time.LocalDateTime;
import java.util.Map;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spldeolin.allison1875.base.BaseConfig;
import com.spldeolin.allison1875.docanalyzer.DocAnalyzerConfig;
import com.spldeolin.allison1875.gadget.LineCounterConfig;
import com.spldeolin.allison1875.handlertransformer.HandlerTransformerConfig;
import com.spldeolin.allison1875.inspector.InspectorConfig;
import com.spldeolin.allison1875.persistencegenerator.PersistenceGeneratorConfig;
import com.spldeolin.allison1875.querytransformer.QueryTransformerConfig;

/**
 * @author Deolin 2020-12-09
 */
public class ConfigConstant {

    public static final BaseConfig baseConfig = new BaseConfig();

    public static final DocAnalyzerConfig docAnalyzerConfig = new DocAnalyzerConfig();

    public static final LineCounterConfig lineCountConfig = new LineCounterConfig();

    public static final HandlerTransformerConfig handlerTransformerConfig = new HandlerTransformerConfig();

    public static final InspectorConfig inspectorConfig = new InspectorConfig();

    public static final PersistenceGeneratorConfig persistenceGeneratorConfig = new PersistenceGeneratorConfig();

    public static final QueryTransformerConfig queryTransformerConfig = new QueryTransformerConfig();

    static {

        baseConfig.setRedisAddress("redis://127.0.0.1:6379");
        baseConfig.setRedisPassword("root");

        docAnalyzerConfig.setYapiUrl("http://127.0.0.1:3000");

        lineCountConfig.setTypePostfix(Lists.newArrayList("ServiceImpl"));
        lineCountConfig.setRankListSize(10);
        lineCountConfig.setDisplayThreshold(10);
        lineCountConfig.setDangerThreshold(500);

        handlerTransformerConfig.setPageTypeQualifier("com.github.pagehelper.PageInfo");

        inspectorConfig.setTargetFileSince(LocalDateTime.of(2020, 9, 7, 0, 0));

        persistenceGeneratorConfig.setQueryPredicateQualifier("com.spldeolin.beginningmind.support.QueryPredicate");
        persistenceGeneratorConfig.setIsEntityUsingAlias(false);
        persistenceGeneratorConfig.setIsEntityEndWithEntity(true);
        persistenceGeneratorConfig.setDeletedSql("delete_flag = TRUE");
        persistenceGeneratorConfig.setNotDeletedSql("delete_flag = FALSE");
        persistenceGeneratorConfig.setHiddenColumns(Lists.newArrayList("hidden_memo"));
        persistenceGeneratorConfig.setNotKeyColumns(Lists.newArrayList("create_staff_id", "update_staff_id"));
        persistenceGeneratorConfig.setSuperEntityQualifier("com.spldeolin.beginningmind.ancestor.EntityAncestor");
        persistenceGeneratorConfig.setAlreadyInSuperEntity(Lists.newArrayList("id", "inserted_at", "updated_at"));
        persistenceGeneratorConfig.setDisableInsert(false);
        persistenceGeneratorConfig.setDisableBatchInsertEvenNull(false);
        persistenceGeneratorConfig.setDisableBatchUpdateEvenNull(false);
        persistenceGeneratorConfig.setDisableQueryById(false);
        persistenceGeneratorConfig.setDisableUpdateById(false);
        persistenceGeneratorConfig.setDisableUpdateByIdEvenNull(false);
        persistenceGeneratorConfig.setDisableQueryByIds(false);
        persistenceGeneratorConfig.setDisableQueryByIdsEachId(false);
        persistenceGeneratorConfig.setDisableQueryByKey(false);
        persistenceGeneratorConfig.setDisableDeleteByKey(false);
        persistenceGeneratorConfig.setDisableQueryByKeys(false);
        persistenceGeneratorConfig.setDisableQueryByEntity(false);
        persistenceGeneratorConfig.setDisableListAll(false);

        Map<String, String> entityCommonPropertyTypes = Maps.newHashMap();
        entityCommonPropertyTypes.put("id", "Long");
        entityCommonPropertyTypes.put("insertedAt", "LocalDateTime");
        entityCommonPropertyTypes.put("updatedAt", "LocalDateTime");
        queryTransformerConfig.setEntityCommonPropertyTypes(entityCommonPropertyTypes);
    }

}