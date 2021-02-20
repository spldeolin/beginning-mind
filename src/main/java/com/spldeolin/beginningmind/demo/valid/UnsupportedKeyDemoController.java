package com.spldeolin.beginningmind.demo.valid;

import java.util.Map;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.valid.annotation.UnsupportedKey;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Deolin 2021-02-20
 */
@Slf4j
@RestController
public class UnsupportedKeyDemoController {

    @Data
    public static class ReqDto {

        @UnsupportedKey({"a", "b"})
        private Map<String, String> stringMap;

    }

    @PostMapping("/uk")
    void uk(@RequestBody @Valid ReqDto req) {
        log.info(req.toString());
    }

}