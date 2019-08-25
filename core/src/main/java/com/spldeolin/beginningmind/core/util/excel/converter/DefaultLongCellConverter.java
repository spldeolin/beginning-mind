package com.spldeolin.beginningmind.core.util.excel.converter;

import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.excel.exception.CellConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultLongCellConverter implements CellConverter<Long> {

    @Override
    public String writeToCellContent(@Nonnull Long obj) {
        return obj.toString();
    }

    @Override
    public Long readFromCellContent(@Nonnull String string) throws CellConverterReadException {
        try {
            return Long.valueOf(string);
        } catch (Exception e) {
            throw new CellConverterReadException();
        }
    }

}
