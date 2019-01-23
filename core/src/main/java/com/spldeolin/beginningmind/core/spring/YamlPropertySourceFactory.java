package com.spldeolin.beginningmind.core.spring;

import java.io.IOException;
import java.util.List;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 与@PropertySource注解结合使用，使前者能够读取.yml配置文件
 *
 * @author Deolin 2019-01-23
 */
public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    @NonNull
    public PropertySource createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        List<PropertySource<?>> propertySourceList = new YamlPropertySourceLoader()
                .load(resource.getResource().getFilename(), resource.getResource());

        if (!propertySourceList.isEmpty()) {
            return propertySourceList.get(0);
        }

        return super.createPropertySource(name, resource);
    }
}
