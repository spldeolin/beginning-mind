package com.spldeolin.beginningmind.core.util.excel.formatter;

import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.excel.exception.ConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultDoubleConverter implements Converter<Double> {

    @Override
    public String write(@Nonnull Double obj) {
        return obj.toString();
    }

    @Override
    public Double read(@Nonnull String string) throws ConverterReadException {
        try {
            return Double.valueOf(string);
        } catch (Exception e) {
            throw new ConverterReadException();
        }
    }

}
