package com.perez.ulises.esparpereznews.preferences;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.perez.ulises.esparpereznews.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment {

    public MapsFragment getInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }
    public MapsFragment() {}

    private Geocoder mGeocoder;
    private SupportMapFragment mMapFragment;
    private List<Address> mAddresses = null;
    private Marker mMarker;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.maps_fragment, container, false);
        mGeocoder = new Geocoder(getContext(), Locale.getDefault());
        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            mMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {

                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            try {
                                mAddresses = mGeocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (IllegalArgumentException illegalArgumentException) {
                                illegalArgumentException.printStackTrace();
                            }

                            if (mAddresses == null || mAddresses.size() == 0) {

                            } else {
                                Address address = mAddresses.get(0);
                                if (mMarker != null) {
                                    mMarker.remove();
                                }
                                mMarker =  googleMap.addMarker(new MarkerOptions().position(latLng)
                                        .title(address.getCountryName()));
                                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                                //                                latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            }
                        }
                    });
                }
            });
        }

        getChildFragmentManager().beginTransaction().replace(R.id.map, mMapFragment).commit();

        return view;
    }
}
