package com.spldeolin.beginningmind.util.excel.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.util.excel.exception.CellConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultJavaUtilDateCellConverter implements CellConverter<Date> {

    private static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<>();

    static {
        sdf.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String writeToCellContent(@Nonnull Date obj) {
        return sdf.get().format(obj);
    }

    @Override
    public Date readFromCellContent(@Nonnull String string) throws CellConverterReadException {
        try {
            return sdf.get().parse(string);
        } catch (Exception e) {
            throw new CellConverterReadException();
        }
    }

}
