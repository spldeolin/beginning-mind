package com.spldeolin.beginningmind.util.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import com.spldeolin.beginningmind.util.excel.annotation.ExcelColumn;
import com.spldeolin.beginningmind.util.excel.annotation.ExcelSheet;
import com.spldeolin.beginningmind.util.excel.converter.CellConverter;
import com.spldeolin.beginningmind.util.excel.exception.ExcelCellContentInvalidException;
import com.spldeolin.beginningmind.util.excel.exception.ExcelReadException;
import com.spldeolin.beginningmind.util.excel.exception.ExcelWriteException;
import lombok.extern.log4j.Log4j2;

/**
 * Excel读写工具类
 *
 * 用法：
 * 1. 定义一个POJO，声明@ExcelSheet
 * 2. 根据需要在POJO内定义field，并为每个field声明@ExcelColumn
 * 3. 对于复杂类型的field，建议指定自定义CellConverter以提供类型转换策略，如java.util.Date、BigDecimal等；
 * 对于简单类型的field，则不需要，如String、Integer、Long等
 * 4. 调用readExcel将Excel读取成List of Objects，所有单元格内的内容会被当作是文本处理
 * 5. 调用writeExcel将List of Objects生成成Excel文件，所有单元格的内容将会是文本
 * 6. 如果读取失败，将会抛出ParseInvalidException，捕获并解析这个异常对象可以获取出错的行列
 *
 * @author Deolin 2018/07/07
 * @see ExcelSheet
 * @see ExcelColumn
 * @see CellConverter
 * @see ExcelCellContentInvalidException
 */
@Log4j2
public class ExcelUtils {

    private ExcelUtils() {
        throw new UnsupportedOperationException("Never instantiate me.");
    }

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(MultipartFile multipartFile, Class<T> clazz) throws ExcelReadException {
        try {
            return ExcelReader.readExcel(multipartFile, clazz);
        } catch (ExcelCellContentInvalidException e) {
            throw new ExcelReadException(e);
        } catch (IOException e) {
            throw new ExcelReadException(e);
        }
    }

    /**
     * 读取Excel
     */
    public static <T> List<T> readExcel(File file, Class<T> clazz) throws ExcelReadException {
        try {
            return ExcelReader.readExcel(file, clazz);
        } catch (ExcelCellContentInvalidException e) {
            throw new ExcelReadException(e);
        }
    }

    /**
     * 生成Excel
     */
    public static <T> void writeExcel(File file, Class<T> clazz, List<T> data) throws ExcelWriteException {
        ExcelWriter.writeExcel(file, clazz, data);
    }

    /**
     * 生成Excel
     */
    public static <T> void writeExcel(HttpServletResponse response, String fileBaseName, Class<T> clazz, List<T> data)
            throws ExcelWriteException {
        ExcelWriter.writeExcel(response, fileBaseName, clazz, data);
    }

}
