package com.spldeolin.beginningmind.allison1875.module;

import com.spldeolin.allison1875.base.ancestor.Allison1875MainProcessor;
import com.spldeolin.allison1875.base.ancestor.Allison1875Module;
import com.spldeolin.allison1875.base.util.ValidateUtils;
import com.spldeolin.allison1875.handlertransformer.HandlerTransformerConfig;
import com.spldeolin.allison1875.handlertransformer.handle.FieldHandle;
import com.spldeolin.allison1875.handlertransformer.processor.HandlerTransformer;
import com.spldeolin.beginningmind.allison1875.handle.handlertransformer.JsonSerializeFieldHandle;

/**
 * @author Deolin 2021-01-21
 */
public class BmHandlerTransformerModule extends Allison1875Module {

    private final HandlerTransformerConfig handlerTransformerConfig;

    public BmHandlerTransformerModule(HandlerTransformerConfig handlerTransformerConfig) {
        this.handlerTransformerConfig = handlerTransformerConfig;
    }

    @Override
    public Class<? extends Allison1875MainProcessor> provideMainProcessorType() {
        return HandlerTransformer.class;
    }

    @Override
    protected void configure() {
        bind(FieldHandle.class).toInstance(new JsonSerializeFieldHandle());
        ValidateUtils.ensureValid(handlerTransformerConfig);
        bind(HandlerTransformerConfig.class).toInstance(handlerTransformerConfig);
    }

}