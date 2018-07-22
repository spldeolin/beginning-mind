package com.spldeolin.beginningmind.core.test;

import org.junit.Test;
import com.spldeolin.beginningmind.core.excel.ExcelColumn;
import com.spldeolin.beginningmind.core.excel.ExcelSheet;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author deoli 2018/06/08
 */
@Log4j2
public class ExcelsTest {

    @Test
    public void testExcels() {
        //File file = new File("C:\\Users\\Deolin\\Desktop\\Excel.xlsx");
        //List<Student> students = Excels.readExcel(file, Student.class);
        //students.forEach(log::info);

        String columnName = "AAAAAB";
        int sum = 0;

        for (int i = 0; i < columnName.length(); i++) {
            sum *= 26;
            sum += (columnName.charAt(i) - 'A' + 1);
        }
        System.out.println(sum);
    }

    @ExcelSheet(sheetName = "学生")
    @Data
    public static class Student {

        @ExcelColumn(firstColumnName = "姓名")
        private String name;

        @ExcelColumn(firstColumnName = "年龄")
        private Integer age;

    }

}
