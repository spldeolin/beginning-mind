package com.spldeolin.beginningmind.util.excel.converter;

import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.util.excel.exception.CellConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultFloatCellConverter implements CellConverter<Float> {

    @Override
    public String writeToCellContent(@Nonnull Float obj) {
        return obj.toString();
    }

    @Override
    public Float readFromCellContent(@Nonnull String string) throws CellConverterReadException {
        try {
            return Float.valueOf(string);
        } catch (Exception e) {
            throw new CellConverterReadException();
        }
    }

}
