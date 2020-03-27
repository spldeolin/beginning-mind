package com.spldeolin.beginningmind.util.excel.converter;

import java.time.LocalDateTime;
import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.util.TimeUtils;
import com.spldeolin.beginningmind.util.excel.exception.CellConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultLocalDateTimeCellConverter implements CellConverter<LocalDateTime> {

    @Override
    public String writeToCellContent(@Nonnull LocalDateTime obj) {
        return TimeUtils.DEFAULT_DATE_TIME_FORMATTER.format(obj);
    }

    @Override
    public LocalDateTime readFromCellContent(@Nonnull String string) throws CellConverterReadException {
        try {
            return LocalDateTime.parse(string, TimeUtils.DEFAULT_DATE_TIME_FORMATTER);
        } catch (Exception e) {
            throw new CellConverterReadException();
        }
    }

}
