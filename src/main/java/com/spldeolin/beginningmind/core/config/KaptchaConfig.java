package com.spldeolin.beginningmind.core.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

/**
 * 图片验证码
 *
 * @author Deolin 2018/12/01
 */
@Component
public class KaptchaConfig {

    @Bean
    public Producer producer() {
        Properties kaptchaProperties = new Properties();

        kaptchaProperties.put(Constants.KAPTCHA_BORDER, "no");
        kaptchaProperties.put(Constants.KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");

        Config config = new Config(kaptchaProperties);
        return config.getProducerImpl();
    }

}
