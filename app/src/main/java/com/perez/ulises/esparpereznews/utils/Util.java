package com.perez.ulises.esparpereznews.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {

    public static String format(Date date) {

        Locale loc = new Locale("es","MX");

        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", loc);
        return sdf.format(date);
    }
}
