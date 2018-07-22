package com.spldeolin.beginningmind.core.test;

import java.io.File;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;
import com.spldeolin.beginningmind.core.excel.ExcelColumn;
import com.spldeolin.beginningmind.core.excel.ExcelSheet;
import com.spldeolin.beginningmind.core.excel.Excels2;
import com.spldeolin.beginningmind.core.excel.Formatter;
import com.spldeolin.beginningmind.core.excel.ParseInvalidException;
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
//        File file = new File("C:\\Users\\Deolin\\Desktop\\output.xlsx");
//        Excels2.writeExcel(file, Person.class, Lists.newArrayList());

    }

    @ExcelSheet(sheetName = "Sheet1 (3)", startingRowNumber = 2)
    @Data
    private static class Person {

        @ExcelColumn(firstColumnName = "货主名称")
        private Integer name;

        @ExcelColumn(firstColumnName = "主要联系人", formatter = AgeFormatter.class)
        private String realAge;

        // TODO mustNull会绑定为null，说明有一个地方不应该直接continue
        @ExcelColumn(firstColumnName = "阿斯顿阿斯顿")
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
