package com.lesson6.util;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;

public class DateUtil {

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate dateToLocalDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    public static Date beginningOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, 0, 01 );
        return calendar.getTime();
    }

    public static Date endOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, 11, 31 );
        return calendar.getTime();
    }


    public static String getDateInStringBeginningOfYear(int year) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(beginningOfYear(year));
    }

    public static String getDateInStringEndOfYear(int year) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(endOfYear(year));
    }
}
