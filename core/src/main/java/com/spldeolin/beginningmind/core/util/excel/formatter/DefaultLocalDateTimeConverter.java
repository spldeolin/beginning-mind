package com.spldeolin.beginningmind.core.util.excel.formatter;

import java.time.LocalDateTime;
import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.Times;
import com.spldeolin.beginningmind.core.util.excel.exception.ConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultLocalDateTimeConverter implements Converter<LocalDateTime> {

    @Override
    public String write(@Nonnull LocalDateTime obj) {
        return Times.DEFAULT_DATE_TIME_FORMATTER.format(obj);
    }

    @Override
    public LocalDateTime read(@Nonnull String string) throws ConverterReadException {
        try {
            return LocalDateTime.parse(string, Times.DEFAULT_DATE_TIME_FORMATTER);
        } catch (Exception e) {
            throw new ConverterReadException();
        }
    }

}
