package com.spldeolin.beginningmind.core.util.excel.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nonnull;
import com.spldeolin.beginningmind.core.util.excel.exception.ConverterReadException;

/**
 * @author Deolin 2019-08-23
 */
public class DefaultJavaUtilDateConverter implements Converter<Date> {

    private static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<>();

    static {
        sdf.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String write(@Nonnull Date obj) {
        return sdf.get().format(obj);
    }

    @Override
    public Date read(@Nonnull String string) throws ConverterReadException {
        try {
            return sdf.get().parse(string);
        } catch (Exception e) {
            throw new ConverterReadException();
        }
    }

}
