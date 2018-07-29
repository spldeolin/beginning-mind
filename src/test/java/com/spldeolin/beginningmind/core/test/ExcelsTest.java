package com.spldeolin.beginningmind.core.test;

import java.io.File;
import java.util.List;
import org.junit.Test;
import com.spldeolin.beginningmind.core.excel.ExcelColumn;
import com.spldeolin.beginningmind.core.excel.ExcelSheet;
import com.spldeolin.beginningmind.core.excel.Excels;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

/**
 * @author deoli 2018/06/08
 */
@Log4j2
public class ExcelsTest {

    @Test
    @SneakyThrows
    public void testExcels() {

        File input = new File("C:\\Users\\Deolin\\Desktop\\input.xlsx");
        File output = new File("C:\\Users\\Deolin\\Desktop\\output.xlsx");

        List<Person> persons = Excels.readExcel(input, Person.class);
        Excels.writeExcel(output, Person.class, persons);
    }

    @ExcelSheet(sheetName = "测试Sheet", startingRowNumber = 2)
    @Data
    private static class Person {

        @ExcelColumn(firstColumnName = "名称")
        private String name;

        @ExcelColumn(firstColumnName = "手机")
        private String mobile;

    }

}
