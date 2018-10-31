package com.perez.ulises.esparpereznews.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

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
        this.pref = mContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    @Override
    public void loadLocation() {
        
    }

    @Override
    public void saveLocation(double latitude, double longitud) {
        try {
            mAddresses = mGeocoder.getFromLocation(latitude, longitud, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mAddresses != null || mAddresses.size() != 0) {
            //TODO guardar en el shared preferences
            Address address = mAddresses.get(0);
            String location = address.getCountryName();
            String cc = address.getCountryCode();
            saveLocation(cc);
            mListener.onSavedLocation(latitude, longitud, location);
        }
    }

    private void saveLocation(String cc) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("cc",cc);
        editor.commit();
    }

}



//                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                        @Override
//                        public void onMapClick(LatLng latLng) {
//
//
//
//                            try {
//                                mAddresses = mGeocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//
//                            if (mAddresses == null || mAddresses.size() == 0) {
//
//                            } else {
//                                Address address = mAddresses.get(0);
//                                if (mMarker != null) {
//                                    mMarker.remove();
//                                }
//                                mMarker =  googleMap.addMarker(new MarkerOptions().position(latLng)
//                                        .title(address.getCountryName()));
//                                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                                //                                latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                            }
//                        }
//                    });
