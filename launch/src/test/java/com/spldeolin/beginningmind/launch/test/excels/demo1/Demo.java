package com.spldeolin.beginningmind.launch.test.excels.demo1;

import java.io.File;
import java.util.List;
import com.spldeolin.beginningmind.core.util.excel.Excels;
import com.spldeolin.beginningmind.core.util.excel.exception.ExcelAnalyzeException;
import com.spldeolin.beginningmind.core.util.excel.exception.ExcelCellContentInvalidException;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2019-08-22
 */
@Log4j2
public class Demo {

    public static void main(String[] args) {
        try {
            List<DemoDTO> dtos = Excels.readExcel(new File(
                    "/Users/deolin/Documents/project-repo/beginning-mind/core/src/main/java/com/spldeolin"
                            + "/beginningmind/core/util/demo1/ready-to-read.xlsx"), DemoDTO.class);
            dtos.forEach(log::info);

            Excels.writeExcel(new File("/Users/deolin/Documents/project-repo/beginning-mind/core/src/main/java/com"
                    + "/spldeolin/beginningmind/core/util/demo1/aaa.xlsx"), DemoDTO.class, dtos);

        } catch (ExcelCellContentInvalidException e) {
            e.getParseInvalids().forEach(log::info);
        } catch (ExcelAnalyzeException e) {
            log.info(e.getMessage());
        }

    }

}
