package com.spldeolin.beginningmind.core.util.excel.converter;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.spldeolin.beginningmind.core.util.excel.exception.DefaultCellConverterAbsentException;

/**
 * Excels默认转换器工厂
 *
 * @author Deolin 2019-08-23
 */
public class DefalutCellConverterFactory {

    public static <T> CellConverter<?> produceConverter(Class<T> type) throws DefaultCellConverterAbsentException {
        if (type == String.class) {
            return new DefaultStringCellConverter();

        } else if (type == Integer.class) {
            return new DefaultIntegerCellConverter();

        } else if (type == Long.class) {
            return new DefaultLongCellConverter();

        } else if (type == Float.class) {
            return new DefaultFloatCellConverter();

        } else if (type == Double.class) {
            return new DefaultDoubleCellConverter();

        } else if (type == BigDecimal.class) {
            return new DefaultBigDecimalCellConverter();

        } else if (type == Boolean.class) {
            return new DefaultBooleanCellConverter();

        } else if (type == LocalDateTime.class) {
            return new DefaultLocalDateTimeCellConverter();

        } else if (type == Date.class) {
            return new DefaultJavaUtilDateCellConverter();

        } else {
            throw new DefaultCellConverterAbsentException();
        }
    }

}
