package com.spldeolin.beginningmind.launch.test.excels.demo1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.excel.converter.CellConverter;
import com.spldeolin.beginningmind.core.util.excel.exception.CellConverterReadException;

/**
 * @author Deolin 2019-08-25
 */
public class CustomCellConverter implements CellConverter<Date> {


    @Override
    public String writeToCellContent(@Nonnull Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    @Override
    public Date readFromCellContent(@Nonnull String string) throws CellConverterReadException {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string);
        } catch (ParseException e) {
            throw new CellConverterReadException();
        }
    }

}
