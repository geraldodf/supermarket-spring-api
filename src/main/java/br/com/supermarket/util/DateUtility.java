package br.com.supermarket.util;


import java.time.LocalDateTime;

public class DateUtility {

    public static String getCurrentDateString() {
        LocalDateTime now = LocalDateTime.now();
        return new StringBuilder()
                .append(now.getDayOfMonth())
                .append("/")
                .append(now.getMonthValue())
                .append("/")
                .append(now.getYear()).toString();
    }

    public static String getCurrentTimeString() {
        LocalDateTime now = LocalDateTime.now();
        return new StringBuilder().append(now.getHour())
                .append(":")
                .append(now.getMinute())
                .append(":")
                .append(now.getSecond())
                .toString();
    }

    public static String getTimeDateCurrentString(){
        return getCurrentDateString() + " - " + getCurrentTimeString();
    }

}
