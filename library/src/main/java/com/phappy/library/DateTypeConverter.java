package com.phappy.library;

import java.util.Date;

public class DateTypeConverter {


    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);/*DateUtils.getUtcDateFromGmtDate(value)*/
    }

    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}