package com.perez.ulises.esparpereznews.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    public static String format(long milis) {
        Locale loc = new Locale("es","MX");

        if (milis == 0) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", loc);
        return sdf.format(milis);
    }

    public static long format(String date) {
        if (!date.isEmpty()) {
            String segments[] = date.split("-");
            int dd = Integer.parseInt(segments[0]);
            int mm = Integer.parseInt(segments[1]);
            int yyyy = Integer.parseInt(segments[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, yyyy);
            calendar.set(Calendar.MONTH, mm);
            calendar.set(Calendar.DAY_OF_MONTH, dd);
            return calendar.getTimeInMillis();
        } else {
            return 0;
        }
    }

    public static Address location(Context context, double latitude, double longitud) {
        List<Address> mAddresses = null;
        Address address = null;
        Geocoder mGeocoder = new Geocoder(context, Locale.getDefault());
        try {
            mAddresses = mGeocoder.getFromLocation(latitude, longitud, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mAddresses != null && mAddresses.size() != 0) {
            address = mAddresses.get(0);
        }
        return address;
    }

    public static Address location(Context context, String location) {
        List<Address> mAddresses = null;
        Address address = null;
        Geocoder mGeocoder = new Geocoder(context, Locale.getDefault());
        try {
            mAddresses = mGeocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mAddresses != null && mAddresses.size() != 0) {
            address = mAddresses.get(0);
        }
        return address;
    }
}
