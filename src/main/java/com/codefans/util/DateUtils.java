package com.codefans.util;

import sun.util.calendar.CalendarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: codefans
 * @Date: 2019-04-14 0:52
 */

public class DateUtils {

    private static final String PATTERN_DEFAULT = "yyyy-MM-dd";
    private static final String PATTERN_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    static final int LEAP_MONTH_LENGTH[]
            = {31,29,31,30,31,30,31,31,30,31,30,31}; // 0-based

    static final int MONTH_LENGTH[]
            = {31,28,31,30,31,30,31,31,30,31,30,31}; // 0-based

    private static ThreadLocal<SimpleDateFormat> defaultDateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return  new SimpleDateFormat(PATTERN_DEFAULT);
        }
    };

    private static ThreadLocal<SimpleDateFormat> fullDateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return  new SimpleDateFormat(PATTERN_YYYYMMDDHHMMSS);
        }
    };

    public static Date parseYYYYMMDD(String dateStr) throws ParseException {
        SimpleDateFormat sdf = defaultDateFormat.get();
        return sdf.parse(dateStr);
    }

    public static Date parseYYYYMMDDHHMMSS(String dateStr) throws ParseException {
        SimpleDateFormat sdf = fullDateFormat.get();
        return sdf.parse(dateStr);
    }

    public static String formatYYYYMMDD(Date date) throws ParseException {
        SimpleDateFormat sdf = defaultDateFormat.get();
        return sdf.format(date);
    }

    public static String formatYYYYMMDDHHMMSS(Date date) throws ParseException {
        SimpleDateFormat sdf = fullDateFormat.get();
        return sdf.format(date);
    }

    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public static int maxDateOfMonth(Calendar calendar){
        return CalendarUtils.isGregorianLeapYear(calendar.get(Calendar.YEAR))?LEAP_MONTH_LENGTH[calendar.get(Calendar.MONTH)]:MONTH_LENGTH[calendar.get(Calendar.MONTH)];
    }

    public static void main(String[] args) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("days=" + days);

    }

}
