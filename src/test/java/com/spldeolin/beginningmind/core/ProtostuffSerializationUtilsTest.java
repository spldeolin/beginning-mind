package com.spldeolin.beginningmind.core;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.junit.Test;
import com.spldeolin.beginningmind.core.cache.util.ProtostuffSerializationUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2018/06/27
 */
@Log4j2
public class ProtostuffSerializationUtilsTest {

    @Test
    public void test() {
        Tiny tiny = new Tiny().setName("姓名").setAge(18).setAt(LocalDateTime.now());
        byte[] data = ProtostuffSerializationUtils.serialize(tiny);
        log.info(new String(data));

        log.info(ProtostuffSerializationUtils.deserialize(data, Tiny.class));
    }

    @Data
    @Accessors(chain = true)
    private static class Tiny implements Serializable {

        private String name;

        private Integer age;

        private LocalDateTime at;

        private static final long serialVersionUID = 1L;

    }

}
