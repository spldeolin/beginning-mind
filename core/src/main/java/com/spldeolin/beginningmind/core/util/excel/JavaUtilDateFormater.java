package com.spldeolin.beginningmind.core.util.excel;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.jetbrains.annotations.NotNull;
import com.spldeolin.beginningmind.core.util.excel.formatter.Formatter;

/**
 * @author Deolin 2019-08-22
 */
public class JavaUtilDateFormater implements Formatter<Date> {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String format(@NotNull Date date) throws Exception {
        return sdf.format(date);
    }

    @Override
    public Date parse(String string) throws Exception {
        return sdf.parse(string);
    }

}
