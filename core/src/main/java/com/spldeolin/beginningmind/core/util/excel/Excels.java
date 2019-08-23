package com.spldeolin.beginningmind.core.util.excel;

import java.io.File;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.spldeolin.beginningmind.core.util.excel.annotation.ExcelColumn;
import com.spldeolin.beginningmind.core.util.excel.annotation.ExcelSheet;
import com.spldeolin.beginningmind.core.util.excel.exception.ExcelReadException;
import com.spldeolin.beginningmind.core.util.excel.formatter.Converter;
import lombok.extern.log4j.Log4j2;

/**
 * Excel读写工具类
 *
 * 用法：
 * 1. 定义一个POJO，声明@ExcelSheet
 * 2. 根据需要在POJO内定义field，并为每个field声明@ExcelColumn
 * 3. 对于复杂类型的field，建议指定自定义Converter以提供类型转换策略，如java.util.Date、BigDecimal等；
 * 对于简单类型的field，则不需要，如String、Integer、Long等
 * 4. 调用readExcel将Excel读取成List of Objects，所有单元格内的内容会被当作是文本处理
 * 5. 调用writeExcel将List of Objects生成成Excel文件，所有单元格的内容将会是文本
 * 6. 如果读取失败，将会抛出ParseInvalidException，捕获并解析这个异常对象可以获取出错的行列
 *
 * @author Deolin 2018/07/07
 * @see ExcelSheet
 * @see ExcelColumn
 * @see Converter
 * @see ExcelReadException
 */
@Log4j2
public class Excels {

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(MultipartFile multipartFile, Class<T> clazz) throws ExcelReadException {
        return ExcelReader.readExcel(multipartFile, clazz);
    }

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(File file, Class<T> clazz) throws ExcelReadException {
        return ExcelReader.readExcel(file, clazz);
    }

    /**
     * 生成Excel
     */
    public static <T> void writeExcel(File file, Class<T> clazz, List<T> list) {
        ExcelWriter.writeExcel(file, clazz, list);
    }

}
