package com.perez.ulises.esparpereznews.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {

    public static String format(Date date) {

        Locale loc = new Locale("es","MX");

        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss", loc);
        return sdf.format(date);
    }
}
