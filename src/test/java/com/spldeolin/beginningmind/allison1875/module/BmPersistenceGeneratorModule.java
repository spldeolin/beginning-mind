package com.spldeolin.beginningmind.allison1875.module;

import com.spldeolin.allison1875.base.ancestor.Allison1875MainProcessor;
import com.spldeolin.allison1875.base.ancestor.Allison1875Module;
import com.spldeolin.allison1875.base.util.ValidateUtils;
import com.spldeolin.allison1875.persistencegenerator.PersistenceGeneratorConfig;
import com.spldeolin.allison1875.persistencegenerator.handle.CommentHandle;
import com.spldeolin.allison1875.persistencegenerator.handle.JdbcTypeHandle;
import com.spldeolin.allison1875.persistencegenerator.processor.PersistenceGenerator;
import com.spldeolin.beginningmind.allison1875.config.EnumConfig;
import com.spldeolin.beginningmind.allison1875.handle.persistencegenerator.EnumJdbcTypeHandle;
import com.spldeolin.beginningmind.allison1875.handle.persistencegenerator.TrimEnumAndTypeHandle;

/**
 * @author Deolin 2020-12-08
 */
public class BmPersistenceGeneratorModule extends Allison1875Module {

    private final PersistenceGeneratorConfig persistenceGeneratorConfig;

    private final EnumConfig enumConfig;

    public BmPersistenceGeneratorModule(PersistenceGeneratorConfig persistenceGeneratorConfig, EnumConfig enumConfig) {
        this.persistenceGeneratorConfig = persistenceGeneratorConfig;
        this.enumConfig = enumConfig;
    }

    @Override
    public Class<? extends Allison1875MainProcessor> provideMainProcessorType() {
        return PersistenceGenerator.class;
    }

    @Override
    protected void configure() {
        bind(JdbcTypeHandle.class).to(EnumJdbcTypeHandle.class);
        bind(CommentHandle.class).to(TrimEnumAndTypeHandle.class);
        ValidateUtils.ensureValid(persistenceGeneratorConfig);
        ValidateUtils.ensureValid(enumConfig);
        bind(PersistenceGeneratorConfig.class).toInstance(persistenceGeneratorConfig);
        bind(EnumConfig.class).toInstance(enumConfig);
    }

}