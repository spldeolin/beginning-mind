package com.spldeolin.beginningmind.core.util.excel.converter;

import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.excel.exception.CellConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultIntegerCellConverter implements CellConverter<Integer> {

    @Override
    public String writeToCellContent(@Nonnull Integer integer) {
        return integer.toString();
    }

    @Override
    public Integer readFromCellContent(@Nonnull String string) throws CellConverterReadException {
        try {
            return Integer.valueOf(string);
        } catch (Exception e) {
            throw new CellConverterReadException();
        }
    }

}
