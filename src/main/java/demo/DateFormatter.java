package demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter implements Formatter<Date> {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy时MM月dd日");

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
