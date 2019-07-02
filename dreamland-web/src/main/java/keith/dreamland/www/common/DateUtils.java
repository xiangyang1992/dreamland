package keith.dreamland.www.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static Date StringtoDate(String dateStr, String formatStr) {
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDate(Date rptTime, String s) {
        if (rptTime == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(s);
        String date = simpleDateFormat.format(rptTime);
        return date;
    }
}
