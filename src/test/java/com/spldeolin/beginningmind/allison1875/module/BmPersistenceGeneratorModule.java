package com.spldeolin.beginningmind.allison1875.module;

import java.util.Set;
import com.spldeolin.allison1875.persistencegenerator.PersistenceGeneratorModule;
import com.spldeolin.allison1875.persistencegenerator.handle.GenerateEntityFieldHandle;
import com.spldeolin.allison1875.persistencegenerator.handle.GenerateQueryDesignFieldHandle;
import com.spldeolin.allison1875.persistencegenerator.handle.JdbcTypeHandle;
import com.spldeolin.beginningmind.allison1875.config.EnumConfig;
import com.spldeolin.beginningmind.allison1875.handle.persistencegenerator.DesignatedEntityTypeOverwriteHandle;
import com.spldeolin.beginningmind.allison1875.handle.persistencegenerator.Java8TimeJdbcTypeHandle;

/**
 * @author Deolin 2020-12-08
 */
public class BmPersistenceGeneratorModule extends PersistenceGeneratorModule {

    @Override
    protected Set<Class<?>> provideConfigTypes() {
        Set<Class<?>> result = super.provideConfigTypes();
        result.add(EnumConfig.class);
        return result;
    }

    @Override
    protected void configure() {
        bind(GenerateEntityFieldHandle.class).to(DesignatedEntityTypeOverwriteHandle.class);
        bind(GenerateQueryDesignFieldHandle.class).to(DesignatedEntityTypeOverwriteHandle.class);
        bind(JdbcTypeHandle.class).to(Java8TimeJdbcTypeHandle.class);
    }

}