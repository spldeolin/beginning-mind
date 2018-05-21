package com.spldeolin.beginningmind.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.env.Environment;
import lombok.experimental.UtilityClass;

/**
 * 判断当前profile是什么。
 *
 * @author Deolin 2018/05/21
 */
@UtilityClass
public class ActiveProfile {

    private static Environment environment;

    static {
        environment = ApplicationContext.getBean(Environment.class);
    }

    public boolean isDev() {
        return ArrayUtils.contains(environment.getActiveProfiles(), "dev");
    }

    public boolean isProd() {
        return ArrayUtils.contains(environment.getActiveProfiles(), "prod");
    }

    public boolean isNotProd() {
        return !isProd();
    }

}
