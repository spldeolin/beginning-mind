package com.spldeolin.beginningmind.core.util.excel.converter;

import java.math.BigDecimal;
import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.excel.exception.CellConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultBigDecimalCellConverter implements CellConverter<BigDecimal> {

    @Override
    public String writeToCellContent(@Nonnull BigDecimal obj) {
        return obj.toString();
    }

    @Override
    public BigDecimal readFromCellContent(@Nonnull String string) throws CellConverterReadException {
        try {
            return new BigDecimal(string);
        } catch (Exception e) {
            throw new CellConverterReadException();
        }
    }

}
