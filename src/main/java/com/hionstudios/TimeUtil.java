package com.hionstudios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface TimeUtil {
    Logger LOGGER = Logger.getLogger(TimeUtil.class.getName());

    static long start(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTimeInMillis();
    }

    static long currentTime() {
        return System.currentTimeMillis();
    }

    static String toString(long date) {
        return toString(date, "dd-MM-yyyy HH:mm:ss a");
    }

    static String toString(long date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    static String toString(String format) {
        return new SimpleDateFormat(format).format(currentTime());
    }

    static long getTime() {
        return currentTime();
    }

    static String toDateString(long date) {
        return toString(date, "dd-MM-yyyy");
    }

    static String toDateString() {
        return toString(currentTime(), "dd-MM-yyyy");
    }

    static String toTimeString(long date) {
        return toString(date, "HH:mm:ss");
    }

    static int getCurrentYear() {
        return Integer.parseInt(toString(getTime(), "yyyy"));
    }

    static long today() {
        return start(getTime());
    }

    static long parse(String time, String format) {
        try {
            return new SimpleDateFormat(format).parse(time).getTime();
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    static long parseDay(String day) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(day).getTime();
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return today();
    }
}
