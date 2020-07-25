package com.spldeolin.beginningmind.demo.valid;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.spldeolin.beginningmind.valid.annotation.Mobile;
import com.spldeolin.beginningmind.valid.annotation.NotEmptyEx;
import com.spldeolin.beginningmind.valid.annotation.NotNullEx;
import com.spldeolin.beginningmind.valid.annotation.Options;
import lombok.Data;

/**
 * @author Deolin 2020-07-25
 */
@RestController
public class ValidAnnotationDemoController {

    /*
{
  "a": "23711112222",
  "b": null,
  "c": [],
  "d": [
    null,
    null
  ],
  "e": null,
  "f": [],
  "g": [
    null,
    null
  ],
  "h": 9
}


invalids=[InvalidDto(path=d, value=[null, null], reason=不能有为空的元素),
InvalidDto(path=a, value=23711112222, reason=不是有效的手机号),
InvalidDto(path=b, value=null, reason=不能为空),
InvalidDto(path=g, value=[null, null], reason=不能有为空的元素),
InvalidDto(path=c, value=[], reason=必须有元素),
InvalidDto(path=e, value=null, reason=不能为空),
InvalidDto(path=h, value=9, reason=值不在可选范围内)]


     */
    @PostMapping("/aaaa")
    String aaaa(@RequestBody @Valid Dto dto) {
        return "SUCCESS" + dto;
    }

    @Data
    public static class Dto {

        @Mobile
        private String a;

        @NotEmptyEx
        private List<Integer> b;

        @NotEmptyEx
        private List<Integer> c;

        @NotEmptyEx
        private List<Integer> d;

        @NotNullEx
        private List<Integer> e;

        @NotNullEx
        private List<Integer> f;

        @NotNullEx
        private List<Integer> g;

        @Options({1, 2})
        private Byte h;

    }

}