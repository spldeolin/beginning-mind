package com.spldeolin.beginningmind.core.util.excel.formatter;

import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.excel.exception.ConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultFloatConverter implements Converter<Float> {

    @Override
    public String write(@Nonnull Float obj) {
        return obj.toString();
    }

    @Override
    public Float read(@Nonnull String string) throws ConverterReadException {
        try {
            return Float.valueOf(string);
        } catch (Exception e) {
            throw new ConverterReadException();
        }
    }

}
