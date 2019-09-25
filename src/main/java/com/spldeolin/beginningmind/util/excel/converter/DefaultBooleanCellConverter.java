package com.spldeolin.beginningmind.util.excel.converter;

import javax.annotation.Nonnull;
import org.apache.commons.lang3.BooleanUtils;
import com.spldeolin.beginningmind.util.excel.exception.CellConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultBooleanCellConverter implements CellConverter<Boolean> {

    @Override
    public String writeToCellContent(@Nonnull Boolean obj) {
        return obj.toString();
    }

    @Override
    public Boolean readFromCellContent(@Nonnull String string) throws CellConverterReadException {
        try {
            return BooleanUtils.toBoolean(string);
        } catch (Exception e) {
            throw new CellConverterReadException();
        }
    }

}
