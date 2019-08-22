package com.spldeolin.beginningmind.core.config;

import java.io.IOException;
import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import com.spldeolin.beginningmind.core.util.Jsons;

/**
 * @author Deolin 2019-02-27
 */
@Configuration
public class ElasticsearchConfig {

    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client) {
        return new ElasticsearchTemplate(client, new CustomEntityMapper());
    }

    private static class CustomEntityMapper implements EntityMapper {

        @Override
        public String mapToString(Object object) throws IOException {
            return Jsons.newDefaultObjectMapper().writeValueAsString(object);
        }

        @Override
        public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
            return Jsons.newDefaultObjectMapper().readValue(source, clazz);
        }

    }

}