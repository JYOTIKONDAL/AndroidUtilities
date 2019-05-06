package com.phappy.library;

import android.text.TextUtils;
import android.util.Log;

import java.text.DateFormat;
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

    public static final long DAYS_1 = 86400000;
    public static final String DATE_CHANGED_FORMAT = "yyyy-MM-dd";
    public static final String SIMPLE_DATE_FORMAT = "M/d/yy";
    public static final String WORK_HOUR_FORMAT = "h:mm";
    public static final String FULL_TIME_STAMP = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";

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

    public static String getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        return new StringBuilder(tz.getDisplayName(false, TimeZone.SHORT)).append(tz.getID()).toString();
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

    /**
     * Method to return the difference in time
     *
     * @param time Time to get difference from current
     * @return Difference in time
     */
    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }

    /**
     * Returns an instance of calender
     *
     * @return an instance of calender
     */
    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        // calendar.setTimeZone(TimeZone.getDefault());
        return calendar.getTime();
    }

    public static Calendar datetoCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Date currentDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        calendar.set(Calendar.AM_PM, calendar.get(Calendar.AM_PM));
        return calendar.getTime();
    }

    /**
     * @param dateFormat for desired date format
     * @return current date
     */
    public static String getCurrentDate(String dateFormat) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return df.format(c.getTime());
    }

    /**
     * @param dateFormat for desired date format
     * @return current date
     */
    public static Date getDate(String dateFormat, String date) {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat, Locale.getDefault());
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getToEndDate(long longEndDate) {
        long toEndDateLong = longEndDate - DAYS_1;
        SimpleDateFormat mToEndDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT, Locale.getDefault());
        return mToEndDate.format(new Date(toEndDateLong));
    }

    /**
     * @return current date in dd/MMM/yy format
     */
    public static String getCurrentDate() {
        return getCurrentDate("dd/MM/yyyy");
    }

    /**
     * @return current date in yyyyMMDDHHMMSS (20181121123328) format
     */
    public static String getCurrentDateTimeNum() {

        return getCurrentDate("yyyyMMddHHmmss");
    }

    public static String getDateChangedFormat(String strDate) {

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date1 = null;
        try {
            date1 = date.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return new SimpleDateFormat(DATE_CHANGED_FORMAT, Locale.getDefault()).format(date1);
    }

    public static String getStringFromDateInPattern(Date mDate, String pattern) {
        if (mDate != null) {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(mDate);
        }
        return "";
    }

    public static String getDate(Date mDate) {
        if (mDate != null) {
            DateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
            return dateFormat.format(mDate);
        }
        return "";
    }

    public static Date getDateFromLong(long startDateTime) {
        return new Date(startDateTime);
    }

    public static int getWorkingMinute(String workEnd, String workStart) {
        if (!TextUtils.isEmpty(workEnd) && !TextUtils.isEmpty(workStart)) {
            long timeDifference = getDate(WORK_HOUR_FORMAT, workEnd).getTime() - getDate(WORK_HOUR_FORMAT, workStart).getTime();
            int mins = (int) ((timeDifference / (1000 * 60))); // minutes
            if (mins < 0)
                mins = 0;
            return mins;
        }
        return 0;
    }

    public static Date getMonthLastDate(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);

        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, max);

        return calendar.getTime();
    }

    public static Date getYearLastDate(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, year);

        int max = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        calendar.set(Calendar.DAY_OF_YEAR, max);

        return calendar.getTime();
    }

    /**
     * Set UTC time in GMT
     * @param strDate
     * @return
     */
    public static Date getDateTimeStamp(String strDate) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        //TimeZone utcZone = TimeZone.getTimeZone("UTC");// For set time in UTC
        TimeZone utcZone = TimeZone.getTimeZone("GMT");// For set time in GMT
        date.setTimeZone(utcZone);

        Date date1 = null;
        try {
            date1 = date.parse(strDate);
            Log.i("DATETIME", ">>>DateTime>>>" + date1.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String getDateTimeStamp(Date strDate) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        //TimeZone utcZone = TimeZone.getTimeZone("UTC");// For set time in UTC
        TimeZone utcZone = TimeZone.getTimeZone("GMT");// For set time in GMT
        date.setTimeZone(utcZone);
        return date.format(strDate);
    }

    public static Date getDateTimeStampInLong(String strDate) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date1 = null;
        try {
            date1 = date.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String getFullTimeStamp(long longDate) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());
        date.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = new Date(longDate);
        return date.format(date1);
    }

    public static String getFullTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FULL_TIME_STAMP, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

}
