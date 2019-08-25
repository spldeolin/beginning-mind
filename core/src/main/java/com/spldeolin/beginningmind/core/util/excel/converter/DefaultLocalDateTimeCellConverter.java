package com.spldeolin.beginningmind.core.util.excel.converter;

import java.time.LocalDateTime;
import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.Times;
import com.spldeolin.beginningmind.core.util.excel.exception.CellConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultLocalDateTimeCellConverter implements CellConverter<LocalDateTime> {

    @Override
    public String writeToCellContent(@Nonnull LocalDateTime obj) {
        return Times.DEFAULT_DATE_TIME_FORMATTER.format(obj);
    }

    @Override
    public LocalDateTime readFromCellContent(@Nonnull String string) throws CellConverterReadException {
        try {
            return LocalDateTime.parse(string, Times.DEFAULT_DATE_TIME_FORMATTER);
        } catch (Exception e) {
            throw new CellConverterReadException();
        }
    }

}
