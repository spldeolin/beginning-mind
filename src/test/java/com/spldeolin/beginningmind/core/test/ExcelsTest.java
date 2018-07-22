package com.spldeolin.beginningmind.core.test;

import java.io.File;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import com.spldeolin.beginningmind.core.excel.ExcelColumn;
import com.spldeolin.beginningmind.core.excel.ExcelSheet;
import com.spldeolin.beginningmind.core.excel.Excels;
import com.spldeolin.beginningmind.core.excel.Formatter;
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

    @ExcelSheet(sheetName = "Sheet1 (3)", startingRowNumber = 2)
    @Data
    private static class Person {

        @ExcelColumn(firstColumnName = "货主名称")
        private Integer name;

        @ExcelColumn(firstColumnName = "主要联系人", formatter = AgeFormatter.class)
        private String realAge;

        @ExcelColumn(firstColumnName = "阿斯顿阿斯顿")
        private String mustNull;

    }

    private class AgeFormatter implements Formatter<String> {

        @Override
        public String format(String s) {
            return s;
        }

        @Override
        public String parse(String string) {
            if (!NumberUtils.isCreatable(string)) {
                return null;
            } else {
                int age = (int) Double.parseDouble(string);
                if (age > 18) {
                    return "永远的18岁";
                }
                return age + "岁";
            }
        }

    }

}
