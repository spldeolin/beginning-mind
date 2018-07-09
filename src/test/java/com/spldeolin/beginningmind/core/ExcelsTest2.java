package com.spldeolin.beginningmind.core;

import java.io.File;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import com.spldeolin.beginningmind.core.util.Excels2;
import com.spldeolin.beginningmind.core.util.excel.ExcelColumn;
import com.spldeolin.beginningmind.core.util.excel.ExcelSheet;
import com.spldeolin.beginningmind.core.util.excel.Formatter;
import com.spldeolin.beginningmind.core.util.excel.ParseInvalidException;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author deoli 2018/06/08
 */
@Log4j2
public class ExcelsTest2 {

    @Test
    public void testExcels() {
        File file = new File("C:\\Users\\Deolin\\Desktop\\sample.xlsx");
        try {
            Excels2.readExcel(file, Person.class).forEach(log::info);
        } catch (ParseInvalidException e) {
            e.getParseInvalids().forEach(log::info);
        }
    }

    @ExcelSheet(sheetIndex = 2, startingRowNumber = 2)
    @Data
    private static class Person {

        @ExcelColumn(firstColumnName = "货主名称")
        private Integer name;

        @ExcelColumn(firstColumnName = "主要联系人", formatter = AgeFormatter.class)
        private String realAge;

        @ExcelColumn(firstColumnName = "阿斯顿阿斯顿", defaultValue = "defaultttt")
        private String mustNull;

    }

    private class AgeFormatter implements Formatter<String> {

        @Override
        public String format(String s) {
            return null;
        }

        @Override
        public String parse(String string) {
            //if ("123".equals(string)) {
            //    throw new ServiceException("asdfasdfasdfddd");
            //}
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
