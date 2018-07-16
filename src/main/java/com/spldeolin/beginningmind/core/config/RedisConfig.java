package com.spldeolin.beginningmind.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/07/16
 */
@Configuration
@Log4j2
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> overrideSerializer(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setEnableTransactionSupport(true);

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);

        ProtostuffSerializer valueSerializer = new ProtostuffSerializer();
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(valueSerializer);
        return redisTemplate;
    }

    public static class ProtostuffSerializer implements RedisSerializer<Object> {

        private boolean isEmpty(byte[] data) {
            return (data == null || data.length == 0);
        }

        private final Schema<ProtoWrapper> schema;

        private final ProtoWrapper wrapper;

        private final LinkedBuffer buffer;

        public ProtostuffSerializer() {
            this.wrapper = new ProtoWrapper();
            this.schema = RuntimeSchema.getSchema(ProtoWrapper.class);
            this.buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        }

        @Override
        public byte[] serialize(Object t) throws SerializationException {
            if (t == null) {
                return new byte[0];
            }
            wrapper.data = t;
            try {
                return ProtostuffIOUtil.toByteArray(wrapper, schema, buffer);
            } finally {
                buffer.clear();
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

    }

    private static class ProtoWrapper {

        public Object data;
    }


}
