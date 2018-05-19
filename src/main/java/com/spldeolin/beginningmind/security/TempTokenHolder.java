package com.spldeolin.beginningmind.security;

import java.util.UUID;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Component
@Data
@Log4j2
public class TempTokenHolder {

    private String actuatorToken;

    @Scheduled(fixedDelay = 24 * 3600 * 1000)
    public void initTokenWhenAppStartUp() {
        actuatorToken = UUID.randomUUID().toString();
        log.info("actuatorToken");
        log.info(actuatorToken);
    }

}
