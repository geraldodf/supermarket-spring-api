package br.com.supermarket.util;


import java.time.LocalDateTime;

public class DateUtility {

    public static String getCurrentDateString() {
        LocalDateTime now = LocalDateTime.now();
        return now.getDayOfMonth() +
                "/" +
                now.getMonthValue() +
                "/" +
                now.getYear();
    }

    public static String getCurrentTimeString() {
        LocalDateTime now = LocalDateTime.now();
        return now.getHour() +
                ":" +
                now.getMinute() +
                ":" +
                now.getSecond();
    }

    public static String getTimeDateCurrentString(){
        return getCurrentDateString() + " - " + getCurrentTimeString();
    }

}
