package com.pplive.bip.util;

import com.pplive.bip.report.Frequency;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final DateTimeFormatter HOURLY_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHH");

    public static final DateTimeFormatter DAILY_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    public static final DateTimeFormatter MONTHLY_FORMATTER = DateTimeFormatter.ofPattern("yyMM");

    public static final DateTimeFormatter YEARLY_FORMATTER = DateTimeFormatter.ofPattern("yy");

    public static int addDays(int date, long add) {
        LocalDate dt = LocalDate.parse(String.valueOf(date), DAILY_FORMATTER);
        return Integer.valueOf(dt.plusDays(add).format(DAILY_FORMATTER));
    }

    public static int startDayOfMonth(int month) {
        return month * 100 + 01;
    }

    public static int endDayOfMonth(int month) {
        LocalDate date = LocalDate.parse(String.valueOf(startDayOfMonth(month)), DAILY_FORMATTER);
        String s = date.plusMonths(1).minusDays(1).format(DAILY_FORMATTER);
        return Integer.valueOf(s);
    }

    public static int startDayOfYear(int year) {
        return year * 10000 + 101;
    }

    public static int endDayOfYear(int year) {
        LocalDate date = LocalDate.parse(String.valueOf(startDayOfYear(year)), DAILY_FORMATTER);
        String s = date.plusYears(1).minusDays(1).format(DAILY_FORMATTER);
        return Integer.valueOf(s);
    }

    public static int formatDateTime(LocalDateTime dateTime, Frequency f) {
        String dt="";
        switch (f) {
            case HOURLY:
                dt = dateTime.format(HOURLY_FORMATTER);
                break;
            case DAILY:
                dt = dateTime.format(DAILY_FORMATTER);
                break;
            case WEEKLY:
                LocalDateTime monday = dateTime.minusDays(dateTime.getDayOfWeek().getValue()-1);
                dt = monday.format(DAILY_FORMATTER);
                break;
            case MONTHLY:
                dt = dateTime.format(MONTHLY_FORMATTER);
                break;
            case YEARLY:
                dt = dateTime.format(YEARLY_FORMATTER);
                break;
            default:
        }

        return Integer.valueOf(dt);
    }
}
