package com.perez.ulises.esparpereznews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;

import com.perez.ulises.esparpereznews.R;

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
            int mm = Integer.parseInt(segments[1]) - 1;
            int yyyy = Integer.parseInt(segments[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(yyyy,mm,dd,0,0,0);
            return calendar.getTimeInMillis();
        } else {
            return 0;
        }
    }

    public static String recyclerDate(String date) {
        date = date.substring(0,10);
        String segments[] = date.split("-");
        date = segments[2].concat("-").concat(segments[1]).concat("-").concat(segments[0]);
        return date;
    }

    public static String urlFormat(Context context) {
        String [] catVal = context.getResources().getStringArray(R.array.pref_cat_values);
        String [] langVal = context.getResources().getStringArray(R.array.pref_langs_values);
        String [] freshnessVal = context.getResources().getStringArray(R.array.pref_freshness_values);
        String url = "";
        String sSafe = "safeSearch=";
        SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.preference_key_file), Context.MODE_PRIVATE);
        boolean safe = pref.getBoolean(context.getString(R.string.pref_key_safe), false);
        if (safe) {
            sSafe = sSafe.concat("Strict");
        } else {
            sSafe = sSafe.concat("Off");
        }
        String category = "category=" + catVal[pref.getInt(context.getString(R.string.pref_key_category), 0)];
        String language = "setLang=" + langVal[pref.getInt(context.getString(R.string.pref_key_language), 0)];
        String freshness = "freshness=" + freshnessVal[pref.getInt(context.getString(R.string.pref_key_freshness),0)];
        String since = String.valueOf(pref.getLong(context.getString(R.string.pref_key_since),0)/1000);
        String cc = "cc=" + pref.getString(context.getString(R.string.pref_key_cc), "");
        if (category.equals("category=")) {
            category = "";
        }
        if (!since.isEmpty()) {
            since = "since=" + since + "&sortBy=Date";
        }
        String [] values = {category,freshness,language,since,cc,sSafe};
        for (int i = 0; i< values.length; i++) {
            if (!values[i].isEmpty()) {
                url = url.concat("&").concat(values[i]);
            }
        }
        return url;
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

