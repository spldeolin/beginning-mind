package demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatter implements Formatter<Date> {

    private final SimpleDateFormat sdf = new SimpleDateFormat("HH时mm分ss秒");

    @Override
    public String format(Date date) {
        return sdf.format(date);
    }

    @Override
    public Date parse(String string) {
        try {
            return sdf.parse(string);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
