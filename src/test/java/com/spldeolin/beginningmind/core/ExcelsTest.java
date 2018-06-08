package com.spldeolin.beginningmind.core;

import java.io.File;
import java.util.List;
import org.junit.Test;
import com.spldeolin.beginningmind.core.util.Excels;
import com.spldeolin.beginningmind.core.util.excel.ExcelColumn;
import com.spldeolin.beginningmind.core.util.excel.ExcelSheet;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author deoli 2018/06/08
 */
@Log4j2
public class ExcelsTest {

    @Test
    public void testExcels() {
        File file = new File("C:\\Users\\deoli\\Desktop\\Excel.xlsx");
        List<Student> students = Excels.readExcel(file, Student.class);
        students.forEach(log::info);
    }

    @ExcelSheet(sheetName = "学生")
    @Data
    public static class Student {

        @ExcelColumn(columnName = "姓名")
        private String name;

    }

}
