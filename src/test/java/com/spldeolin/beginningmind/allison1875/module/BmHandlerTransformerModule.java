package com.spldeolin.beginningmind.allison1875.module;

import com.spldeolin.allison1875.handlertransformer.HandlerTransformerModule;
import com.spldeolin.allison1875.handlertransformer.handle.CreateHandlerHandle;
import com.spldeolin.allison1875.handlertransformer.handle.CreateServiceMethodHandle;
import com.spldeolin.allison1875.handlertransformer.handle.DefaultCreateHandlerHandle;
import com.spldeolin.allison1875.handlertransformer.handle.DefaultCreateServiceMethodHandle;
import com.spldeolin.allison1875.handlertransformer.handle.FieldHandle;
import com.spldeolin.beginningmind.allison1875.handle.handlertransformer.JsonSerializeFieldHandle;

/**
 * @author Deolin 2021-01-21
 */
public class BmHandlerTransformerModule extends HandlerTransformerModule {

    @Override
    protected void configure() {
        bind(CreateServiceMethodHandle.class).toInstance(new DefaultCreateServiceMethodHandle());
        bind(CreateHandlerHandle.class).toInstance(new DefaultCreateHandlerHandle());
        bind(FieldHandle.class).toInstance(new JsonSerializeFieldHandle());
    }

}