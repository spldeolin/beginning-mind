package com.spldeolin.beginningmind.core.util.excel.formatter;

import java.math.BigDecimal;
import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.excel.exception.ConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultBigDecimalConverter implements Converter<BigDecimal> {

    @Override
    public String write(@Nonnull BigDecimal obj) {
        return obj.toString();
    }

    @Override
    public BigDecimal read(@Nonnull String string) throws ConverterReadException {
        try {
            return new BigDecimal(string);
        } catch (Exception e) {
            throw new ConverterReadException();
        }
    }

}
