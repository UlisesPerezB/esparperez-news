package com.perez.ulises.esparpereznews.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.utils.Util;

public class MapsInteractor implements MapsInterfaces.IMapsInteractor, PreferenceInteractor.onLocationChanged {

    private MapsInterfaces.IMapsListener mListener;
    private Context mContext;
    private SharedPreferences mPref;

    public MapsInteractor(){
    }

    public MapsInteractor(MapsInterfaces.IMapsListener listener, Context context) {
        this.mListener = listener;
        this.mContext = context;
        this.mPref = mContext.getSharedPreferences(mContext.getString(R.string.preference_key_file), Context.MODE_PRIVATE);
    }

    @Override
    public void loadLocation() {
        String location = mPref.getString(mContext.getString(R.string.pref_key_location),"");
        if (!location.isEmpty()) {
            Address address;
            address = Util.location(mContext, location);
            if (address != null) {
                mListener.onSavedLocation(address.getLatitude(), address.getLongitude(), address.getCountryName());
            }
        } else {
            mListener.onNoLocationSaved(mContext.getString(R.string.pref_no_location));
        }
    }

    @Override
    public void addNewMarker(double latitude, double longitud) {
        Address address;
        address = Util.location(mContext, latitude, longitud);
        if (address != null) {
            String location = address.getCountryName();
            mListener.onSavedLocation(latitude,longitud, location);
        }
    }

    @Override
    public void saveCountryCode(Context context, String location) {
        if (!location.isEmpty()) {
            Address address = Util.location(context, location);
            if (address != null) {
                SharedPreferences pref =
                        context.getSharedPreferences(context.getString(R.string.preference_key_file), Context.MODE_PRIVATE);
                String cc = address.getCountryCode();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(context.getString(R.string.pref_key_cc), cc);
                editor.commit();
            }
        }
    }
}