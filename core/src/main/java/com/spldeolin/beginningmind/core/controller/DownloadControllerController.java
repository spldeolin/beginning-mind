package com.spldeolin.beginningmind.core.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.core.util.excel.Excels;

/**
 * @author Deolin 2019-08-25
 */
@Controller
@RequestMapping("/DownloadController")
@Validated
public class DownloadControllerController {

    @GetMapping("/aa")
    void aa(HttpServletResponse response) {
        List<DemoDTO> dtos = Lists.newArrayList(DemoDTO.builder().id(1L).build());

        Excels.writeExcel(response, "测试", DemoDTO.class, dtos);
    }

}