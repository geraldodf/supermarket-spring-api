package br.com.supermarket.util;


import java.time.LocalDateTime;

public class DataUtilitario {

    public static String getDataAtualComoString() {
        LocalDateTime now = LocalDateTime.now();
        return new StringBuilder()
                .append(now.getDayOfMonth())
                .append("/")
                .append(now.getMonthValue())
                .append("/")
                .append(now.getYear()).toString();
    }

    public static String getHorarioAtualString() {
        LocalDateTime now = LocalDateTime.now();
        return new StringBuilder().append(now.getHour())
                .append(":")
                .append(now.getMinute())
                .append(":")
                .append(now.getSecond())
                .toString();
    }

    public static String getHorarioEDataAtualString(){
        return getDataAtualComoString() + " - " + getHorarioAtualString();
    }

}
