package com.spldeolin.beginningmind.core.util.excel.formatter;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.spldeolin.beginningmind.core.util.excel.exception.UnknowDefaultTypeException;

/**
 * Excels默认转换器工厂
 *
 * @author Deolin 2019-08-23
 */
public class DefalutConverterFactory {

    public static <T> Converter<?> produceConverter(Class<T> type) throws UnknowDefaultTypeException {
        if (type == String.class) {
            return new DefaultStringConverter();

        } else if (type == Integer.class) {
            return new DefaultIntegerConverter();

        } else if (type == Long.class) {
            return new DefaultLongConverter();

        } else if (type == Float.class) {
            return new DefaultFloatConverter();

        } else if (type == Double.class) {
            return new DefaultDoubleConverter();

        } else if (type == BigDecimal.class) {
            return new DefaultBigDecimalConverter();

        } else if (type == Boolean.class) {
            return new DefaultBooleanConverter();

        } else if (type == LocalDateTime.class) {
            return new DefaultLocalDateTimeConverter();

        } else if (type == Date.class) {
            return new DefaultJavaUtilDateConverter();

        } else {
            throw new UnknowDefaultTypeException();
        }
    }

}
