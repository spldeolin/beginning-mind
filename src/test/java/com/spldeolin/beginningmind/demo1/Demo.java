package com.spldeolin.beginningmind.demo1;

import java.io.File;
import java.util.List;
import com.spldeolin.beginningmind.util.excel.Excels;
import lombok.extern.log4j.Log4j2;

/**
 * @author Deolin 2019-08-22
 */
@Log4j2
public class Demo {

    public static void main(String[] args) {
        List<DemoDTO> dtos = Excels.readExcel(new File(
                "/Users/deolin/Documents/project-repo/beginning-mind/launch/src/test/java/com/spldeolin"
                        + "/beginningmind/launch/test/excels/demo1/ready-to-read.xlsx"), DemoDTO.class);
        dtos.forEach(log::info);

        Excels.writeExcel(new File(
                "/Users/deolin/Documents/project-repo/beginning-mind/launch/src/test/java/com/spldeolin"
                        + "/beginningmind/launch/test/excels/demo1/aaa.xlsx"), DemoDTO.class, dtos);

    }

}
