package com.spldeolin.beginningmind.core.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * @author Deolin 2018/07/29
 */
public class ProtostuffSerializer implements RedisSerializer<Object> {

    private boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    private final Schema<ProtoWrapper> schema;

    private final ProtoWrapper wrapper;

    private BufferContext buffer;

    public ProtostuffSerializer() {
        wrapper = new ProtoWrapper();
        schema = RuntimeSchema.getSchema(ProtoWrapper.class);
        buffer = new BufferContext();
    }

    @Override
    public byte[] serialize(Object t) throws SerializationException {
        buffer.set(LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        if (t == null) {
            return new byte[0];
        }
        wrapper.data = t;
        try {
            return ProtostuffIOUtil.toByteArray(wrapper, schema, buffer.get());
        } finally {
            buffer.get().clear();
            buffer.remove();
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (isEmpty(bytes)) {
            return null;
        }

        ProtoWrapper newMessage = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, newMessage, schema);
        return newMessage.data;
    }

    private static class ProtoWrapper {

        public Object data;

    }

    private static class BufferContext {

        private final ThreadLocal<LinkedBuffer> linkedBufferThreadLocal = new ThreadLocal<>();

        void set(LinkedBuffer linkedBuffer) {
            linkedBufferThreadLocal.set(linkedBuffer);
        }

        LinkedBuffer get() {
            return linkedBufferThreadLocal.get();
        }

        void remove() {
            linkedBufferThreadLocal.remove();
        }

    }

}


