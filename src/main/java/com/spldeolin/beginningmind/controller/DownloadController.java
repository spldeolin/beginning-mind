package com.spldeolin.beginningmind.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.common.collect.Lists;
import com.spldeolin.beginningmind.util.excel.Excels;

/**
 * @author Deolin 2019-08-25
 */
@Controller
@RequestMapping("/download")
@Validated
public class DownloadController {

    @GetMapping("/aa")
    void aa(HttpServletResponse response) {
        List<DemoDTO> dtos = Lists.newArrayList(DemoDTO.builder().id(1L).build());

        Excels.writeExcel(response, "测试", DemoDTO.class, dtos);
    }

}