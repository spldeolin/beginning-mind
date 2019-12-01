package com.spldeolin.beginningmind.util;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * @author Deolin 2019-11-29
 */
public class ProtostuffUtil {

    private static final ThreadLocal<LinkedBuffer> buffer = ThreadLocal.withInitial(() -> LinkedBuffer.allocate(512));

    public <T> byte[] serialize(T t, Class<T> clazz) {
        if (t == null) {
            return new byte[0];
        }

        Schema<T> schema = RuntimeSchema.getSchema(clazz);

        final byte[] protostuff;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(t, schema, buffer.get());
        } finally {
            buffer.get().clear();
        }
        return protostuff;
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        if (bytes.length == 0) {
            return null;
        }

        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        T t = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, t, schema);
        return t;
    }

}
