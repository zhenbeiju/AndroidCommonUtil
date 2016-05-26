package commanutil.utl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhanglin on 15-6-15.
 */
public class TimeUtil {
    public static String formatTime(String formatter, long timemile) {
        SimpleDateFormat format = new SimpleDateFormat(formatter, Locale.getDefault());
        String result = format.format(new Date(timemile));
        return result;

    }

    public static final String DEFAULT_TIMEFORMATER = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static long getTimemile(String time, String formatter) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        Date date = null;
        try {
            date = sdf.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
