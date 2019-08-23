package com.spldeolin.beginningmind.core.util.excel.formatter;

import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.excel.exception.ConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultIntegerConverter implements Converter<Integer> {

    @Override
    public String write(@Nonnull Integer integer) {
        return integer.toString();
    }

    @Override
    public Integer read(@Nonnull String string) throws ConverterReadException {
        try {
            return Integer.valueOf(string);
        } catch (Exception e) {
            throw new ConverterReadException();
        }
    }

}
