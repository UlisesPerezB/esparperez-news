package com.perez.ulises.esparpereznews.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.perez.ulises.esparpereznews.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsInteractor implements MapsInterfaces.IMapsInteractor {

    private MapsInterfaces.IMapsListener mListener;
    private Context mContext;

    private Geocoder mGeocoder;
    private List<Address> mAddresses = null;
    private SharedPreferences pref;


    public MapsInteractor(MapsInterfaces.IMapsListener listener, Context context) {
        this.mListener = listener;
        this.mContext = context;
        this.mGeocoder = new Geocoder(context, Locale.getDefault());
        this.pref = mContext.getSharedPreferences(mContext.getString(R.string.preference_key_file), Context.MODE_PRIVATE);
    }

    @Override
    public void loadLocation() {

        String location = pref.getString(mContext.getString(R.string.pref_key_location),"");
        Log.i("MAPS", "Location: "+ location);
        if (!location.isEmpty()) {
            try {
                mAddresses = mGeocoder.getFromLocationName(location, 1);
                Address address = mAddresses.get(0);
                mListener.onSavedLocation(address.getLatitude(), address.getLongitude(), address.getCountryName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mListener.onNoLocationSaved(mContext.getString(R.string.pref_no_location));
        }
    }

    @Override
    public void saveLocation(double latitude, double longitud) {
        try {
            mAddresses = mGeocoder.getFromLocation(latitude, longitud, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mAddresses != null && mAddresses.size() != 0) {
            Address address = mAddresses.get(0);
            String location = address.getCountryName();
            String cc = address.getCountryCode();
            storeLocation(location, cc);
            mListener.onSavedLocation(latitude, longitud, location);
        }
    }

    private void storeLocation(String location, String cc) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(mContext.getString(R.string.pref_key_location),location);
        editor.putString(mContext.getString(R.string.pref_key_cc), cc);
        editor.commit();
    }
}