package com.spldeolin.beginningmind.allison1875.config;

import javax.validation.constraints.NotEmpty;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import lombok.Data;

/**
 * @author Deolin 2020-11-14
 */
@Singleton
@Data
public final class EnumConfig extends AbstractModule {

    /**
     * 枚举类型的包名（根据目标工程的情况填写）
     */
    @NotEmpty
    private String enumPackage;

    @Override
    protected void configure() {
        bind(EnumConfig.class).toInstance(this);
    }

}