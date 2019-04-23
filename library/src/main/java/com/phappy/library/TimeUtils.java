package com.phappy.library;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by SinghParamveer on 1/19/2018.
 * Utility class for handle time related functions such as comparison,conversion,formatting etc
 */

public class TimeUtils {

    private static final long MILLIS_IN_MINUTE = 60 * 1000;
    private static final long MILLIS_IN_HOUR = 60 * MILLIS_IN_MINUTE;
    private static final long MILLIS_IN_DAY = 24 * MILLIS_IN_HOUR;
    public static final long SQL_DATE_MAX_VALUE = 106751991167L * 1000;
    public static final long SQL_DATE_MIN_VALUE = -62167219200L * 1000;

    /**
     * @param startDateString date from where comparison to be made
     * @param endDateString   date up to which comparison is required
     * @param dateFormat      the date format to be used for start and end dates
     * @return 0 if equal, 1 if start date is after end date, -1 if end date is after start date
     */
    public int compareDate(String startDateString, String endDateString, String dateFormat) {

        SimpleDateFormat inputDateFromat = new SimpleDateFormat(dateFormat, Locale.getDefault());

        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = inputDateFromat.parse(startDateString);
            endDate = inputDateFromat.parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate.compareTo(endDate);
    }

    /**
     * @param sourceDate       the date which needs to be converted to specific format
     * @param targetDateFormat the format to which the current date is to be converted.
     *                         eg. dd/MM/yyyy hh:mm a for 01/12/2018 10:10 pm
     * @return return the string representation of formatted date
     */
    public String changeDateFormatFromDate(Date sourceDate, String targetDateFormat) {
        if (sourceDate == null || targetDateFormat == null || targetDateFormat.isEmpty()) {
            return "";
        }
        SimpleDateFormat outputDateFormat = new SimpleDateFormat(targetDateFormat, Locale.getDefault());
        return outputDateFormat.format(sourceDate);
    }

    public Date getDateFromFormat(String dateString, String sourceDateFormat) {
        if (dateString == null || dateString.isEmpty()) {
            return new Date();
        }
        SimpleDateFormat inputDateFromat = new SimpleDateFormat(sourceDateFormat, Locale.getDefault());
        Date date = new Date();
        try {
            date = inputDateFromat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date getDateInUTC() {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return calendar.getTime();
    }

    public String changeDateFormat(String dateString, String sourceDateFormat, String targetDateFormat) {
        if (dateString == null || dateString.isEmpty()) {
            return "";
        }
        SimpleDateFormat inputDateFromat = new SimpleDateFormat(sourceDateFormat, Locale.ENGLISH);
        //below line convert time according to UTC (add 5:30)
//        inputDateFromat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        try {
            date = inputDateFromat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputDateFormat = new SimpleDateFormat(targetDateFormat, Locale.getDefault());
        return outputDateFormat.format(date);
    }

    public String msToString(long ms) {
        return msToString(ms, false);
    }

    public String msToString(long ms, boolean shortString) {
        long remainder = ms;

        String daysString = "";
        if (remainder >= MILLIS_IN_DAY) {
            long days = remainder / MILLIS_IN_DAY;
            remainder = remainder % MILLIS_IN_DAY;
            if (days > 0) {
                daysString = days + (days > 1 ? " days " : " day ");
            }
        }
        String hoursString = "";
        if (remainder >= MILLIS_IN_HOUR) {
            long hours = (remainder / MILLIS_IN_HOUR);
            remainder = remainder % MILLIS_IN_HOUR;
            if (hours > 0)
                hoursString = hours + (hours > 1 ? " hours " : " hour ");
        }
        String minsString = "";
        if (remainder >= MILLIS_IN_MINUTE) {
            long mins = (remainder / MILLIS_IN_MINUTE);
            remainder = remainder % MILLIS_IN_MINUTE;
            if (mins > 0)
                minsString = mins + (mins > 1 ? (shortString ? "mins" : " minutes ")
                        : (shortString ? "min" : " minute "));
        }

        String secsString = "";
        if (remainder >= 1000) {
            //Comment to set custom value
//            long secs = (remainder / 1000);
//            if (secs > 0)
//                secsString = secs + (secs > 1 ? " seconds " : " second ");
            secsString = "1 minute";
        } else if (daysString.isEmpty() && hoursString.isEmpty() && minsString.isEmpty()) {
            secsString = "0 minute";
        }
        return daysString + hoursString + minsString + secsString;
    }

    /**
     * @return return the long millis of the day u pass from current date.
     */

    public static long getDayMillis(int numOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, numOfDays);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getWeekMillis(int numOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.WEEK_OF_MONTH, numOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getMonthMillis(int numOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, numOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long dayStartMillisFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long roundDateMillisToMinutes(long dateMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateMillis);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long roundDateToNearestMinutes(long dateMillis, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateMillis);
        int min = calendar.get(Calendar.MINUTE);
        int remainder = min % minutes;
        if (remainder > (minutes / 2))
            min += (minutes - remainder);
        else
            min -= remainder;
        calendar.set(Calendar.MINUTE, min);
        return calendar.getTimeInMillis();
    }

    /**
     * @param milliSeconds     millis which needs to be converted to specific format
     * @param targetDateFormat the format to which the current millis is to be converted.
     *                         eg. dd/MM/yyyy hh:mm a for 01/12/2018 10:10 pm
     * @return return the string representation of formatted date
     */
    public static String getDate(long milliSeconds, String targetDateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(targetDateFormat, Locale.getDefault());

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    /**
     * @param date         the date which needs to be converted in millis
     * @param sourceFormat the format of the date that is to be converted to millis.
     * @return return the long representation of formatted date
     */
    public static long getMillisDate(String date, String sourceFormat) {
        long timeInMilliseconds = 0;

        if (date == null || date.isEmpty()) {
            return timeInMilliseconds;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(sourceFormat, Locale.getDefault());
        try {
            Date mDate = sdf.parse(date);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }
}
