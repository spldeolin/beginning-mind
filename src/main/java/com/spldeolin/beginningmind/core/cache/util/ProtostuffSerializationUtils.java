package com.spldeolin.beginningmind.core.cache.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.experimental.UtilityClass;

/**
 * 基于Protostuff的序列化工具
 * <p>
 * 序列化后的数据不可读
 *
 * @author Deolin 2018/06/27
 */
@UtilityClass
public class ProtostuffSerializationUtils {

    private static LoadingCache<Class, Schema> schemaCache = CacheBuilder.newBuilder()
            .maximumSize(16)
            .build(new CacheLoader<Class, Schema>() {
                @SuppressWarnings("unchecked")
                public Schema load(Class clazz) {
                    return RuntimeSchema.getSchema(clazz);
                }
            });

    /**
     * 序列化
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] serialize(T target) {
        Class<T> clazz = (Class<T>) target.getClass();
        Schema<T> schema = schemaCache.getUnchecked(clazz);
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        final byte[] protostuff;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(target, schema, buffer);
        } finally {
            buffer.clear();
        }
        return protostuff;
    }

    /**
     * 反序列化
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] serializedData, Class<T> clazz) {
        Schema<T> schema = schemaCache.getUnchecked(clazz);
        T target = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(serializedData, target, schema);
        return target;
    }

}
