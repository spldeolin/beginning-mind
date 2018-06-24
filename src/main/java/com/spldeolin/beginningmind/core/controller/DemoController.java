/*
 * Created by IntelliJ IDEA File Templates.
 */

package com.spldeolin.beginningmind.core.controller;

import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.spldeolin.beginningmind.core.api.dto.Page;
import com.spldeolin.beginningmind.core.model.SecurityPermission;
import com.spldeolin.beginningmind.core.model.SecurityRole;
import com.spldeolin.beginningmind.core.valid.ValidableList;
import lombok.Data;

/**
 * 示例
 *
 * @author Deolin 2018/06/01
 */
@RestController
@RequestMapping("/demo")
@Validated
public class DemoController {

    /**
     * 获取资源
     *
     * @param a a值   ddd asd asdf asdf asdf asdf asdf     asdfasdf asasdf asdf asd    asdf asdf asd    asdf
     * @param c c值
     * @param d d值
     * @param input 啊啊        啊啊
     * @return adsfa    sdf
     */
    @PostMapping("/create/{d}")
    Page<SecurityPermission> create(
            @RequestParam String a,
            /* DDD */@RequestParam(required = false) BigDecimal b,
            @RequestParam(defaultValue = "") Integer c,
            @PathVariable Boolean d,
            @RequestBody ValidableList<SecurityRole> input) {
        return null;
    }

    /**
     * 有注释
     * 换行
     * <p>
     * 隔行
     *
     * @param a a值
     * @return 控制
     */
    @GetMapping("/get2")
    String get2(@RequestBody Integer a) {
        return null;
    }

    @GetMapping("/get1")
    String get1(@RequestBody Integer a) {
        return null;
    }

    @PostMapping("/")
    void post() {
    }

    @Data
    private static class InnerDTO {

        private String name;
    }

}

@Data
class DefaultDTO {

    private String name;
}