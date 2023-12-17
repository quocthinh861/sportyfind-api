package com.sportyfind.webapi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String formatDateToString (Date date) {
        if(date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    public static Date formatStringToDate (String date) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.parse(date);
    }

    public static String formatDateToString (Date date, String format) {
        if(date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
}
