package com.spldeolin.beginningmind.util.excel.converter;

import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.util.excel.exception.CellConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultDoubleCellConverter implements CellConverter<Double> {

    @Override
    public String writeToCellContent(@Nonnull Double obj) {
        return obj.toString();
    }

    @Override
    public Double readFromCellContent(@Nonnull String string) throws CellConverterReadException {
        try {
            return Double.valueOf(string);
        } catch (Exception e) {
            throw new CellConverterReadException();
        }
    }

}
