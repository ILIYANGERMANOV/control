package custom.extras.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import custom.extras.MyDebugger;

public class DateUtils {
    public static final String SQL_LITE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final int UPDATE_CHECK_INTERVAL = 3;

    public static Date getNextDateToCheckForUpdate() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, UPDATE_CHECK_INTERVAL);
        return calendar.getTime();
    }

    public static String getCurrentTimeSQLiteFormatted() {
        return formatDateInSQLiteFormat(getCurrentTime());
    }

    public static String formatDateInSQLiteFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(SQL_LITE_DATE_FORMAT, Locale.US);
        return dateFormat.format(date);
    }

    public static Date parseDateFromSQLiteFormat(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SQL_LITE_DATE_FORMAT, Locale.US);
        Date date;
        try {
            //trying to parse date from SQLite format
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            //exception while parsing date, log it
            date = new Date(); //return the current date so there won't be need for null check
            MyDebugger.log("ParseException in parseDateFromSQLiteFormat()", e.getMessage());
            e.printStackTrace();
        }
        return date;
    }

    private static Date getCurrentTime() {
        Date date;
        try {
            date = Calendar.getInstance().getTime();
        } catch (IllegalArgumentException ex) {
            MyDebugger.log("getCurrentTimeSQLiteFormatted()", ex.getMessage());
            date = new Date();
        }
        return date;
    }
}
