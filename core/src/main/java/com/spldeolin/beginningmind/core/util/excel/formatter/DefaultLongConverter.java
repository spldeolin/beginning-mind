package com.spldeolin.beginningmind.core.util.excel.formatter;

import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.excel.exception.ConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultLongConverter implements Converter<Long> {

    @Override
    public String write(@Nonnull Long obj) {
        return obj.toString();
    }

    @Override
    public Long read(@Nonnull String string) throws ConverterReadException {
        try {
            return Long.valueOf(string);
        } catch (Exception e) {
            throw new ConverterReadException();
        }
    }

}
