package com.spldeolin.beginningmind.core.util.excel.formatter;

import javax.annotation.Nonnull;
import org.apache.commons.lang3.BooleanUtils;
import com.spldeolin.beginningmind.core.util.excel.exception.ConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultBooleanConverter implements Converter<Boolean> {

    @Override
    public String write(@Nonnull Boolean obj) {
        return obj.toString();
    }

    @Override
    public Boolean read(@Nonnull String string) throws ConverterReadException {
        try {
            return BooleanUtils.toBoolean(string);
        } catch (Exception e) {
            throw new ConverterReadException();
        }
    }

}
