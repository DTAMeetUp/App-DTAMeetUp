package fr.dta.formtion.dta_meetup.database;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Arnaud on 02/11/2017.
 */

public class DateUtils {
    public static String getMonthFormated(long milliseconds) {
        String months[] = {"JAN", "FEV", "MAR", "AVR", "MAI", "JUN", "JUL", "AOU", "SEP", "OCT", "NOV", "DEC"};
        Calendar date = Calendar.getInstance();
        date.setTime(new Date(milliseconds));

        return months[date.get(Calendar.MONTH)];
    }

    public static int getDayOfMonth(long milliseconds) {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date(milliseconds));
        return date.get(Calendar.DAY_OF_MONTH);
    }

    public static String getDayOfWeek(long milliseconds) {
        String dayOfWeek[] = {"DIM", "LUN", "MAR", "MER", "JEU", "VEN", "SAM"};
        Calendar date = Calendar.getInstance();
        date.setTime(new Date(milliseconds));
        return dayOfWeek[date.get(Calendar.DAY_OF_WEEK)-1];
    }

    public static int getYear(long milliseconds) {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date(milliseconds));
        return date.get(Calendar.YEAR);
    }

    public static int getHour(long milliseconds) {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date(milliseconds));
        return date.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinutes(long milliseconds) {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date(milliseconds));
        return date.get(Calendar.MINUTE);
    }

    public static String getFormatedHour(long milliseconds) {
        return getHour(milliseconds) + ":" + String.format("%02d", getMinutes(milliseconds));
    }
}
