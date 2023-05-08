package com.sportyfind.webapi.Utils;

import ch.qos.logback.core.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomTimeUtil {
    public static String formatDateToString (Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    public static Date formatStringToDate (String date) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.parse(date);
    }


}
