package com.spldeolin.beginningmind.core.controller;

import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.spldeolin.beginningmind.core.controller.annotation.Authorization;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * 用于测试的控制器
 *
 * @author Deolin 2018/08/04
 */
@RestController
@RequestMapping({"a", "b"})
@Validated
@Log4j2
public class TestController {

    public static final String TEST_REQUEST_MAPPING_PREFIX = "/test";

    /**
     * 按打法啊定身法
     *
     * @param a adf
     * @param b asdf
     * @return asdf
     * @author ad
     */
    @PostMapping("/ppp")
    @Authorization(display = "普通请求", menuId = 1L)
    String ln20(String a, String b) {
        return null;
    }

    @GetMapping("/ggg")
    @Authorization(menuId = 1L)
    Object ln28() {
        return null;
    }

    @GetMapping("/pp1p")
    @Authorization(menuId = Authorization.WHAT_EVER, mustHave = true)
    Object ln35() {

        return null;
    }

    @GetMapping("/multimap")
    Multimap ln17() {
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("111", "数字1");
        multimap.put("111", "数字2");
        multimap.put("111", "数字12313");
        multimap.put("111", "数字");

        multimap.putAll("adf", Lists.newArrayList("字母", "char"));

        return multimap;
    }

    @GetMapping("/rawMultimap")
    Object ln66() {
        RawMultimap rawMultimap1 = new RawMultimap();
        rawMultimap1.setKey("1");
        rawMultimap1.setValues(Lists.newArrayList("数字1", "数字2", "数字12313", "数字"));

        RawMultimap rawMultimap2 = new RawMultimap();
        rawMultimap2.setKey("adf");
        rawMultimap2.setValues(Lists.newArrayList("字母", "char"));

        List<RawMultimap> RawMultimaps = Lists.newArrayList(rawMultimap1, rawMultimap2);

        return RawMultimaps;
    }

    @Data
    private static class RawMultimap {

        private String key;

        private List<String> values;

    }

    @PostMapping("/multimapInput")
    void ln92(@RequestBody Multimap<String, String> multimap) {
        log.info(multimap);
    }

}