package com.perez.ulises.esparpereznews.preferences;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.perez.ulises.esparpereznews.R;

import butterknife.BindView;

public class MapsFragment extends Fragment implements MapsInterfaces.IMapsView, PreferenceInterfaces.ILoadMap{

    public MapsFragment getInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }
    public MapsFragment() {}

    private SupportMapFragment mMapFragment;
    private Marker mMarker;
    private GoogleMap mMap;

    private MapsPresenter mPresenter;
    private PreferenceFragment mPrefecence;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.maps_fragment, container, false);
        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            mMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    mMap = googleMap;
                    click(mMap);
                }
            });
        }
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.map, mMapFragment).commit();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter == null) {
            mPresenter = new MapsPresenter(this, getContext());
        }
        if (getParentFragment() instanceof PreferenceFragment) {
            mPrefecence = (PreferenceFragment) getParentFragment();
        }
    }

    void click(GoogleMap googleMap) {
        mPresenter.loadLocation();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mPresenter.saveLocation(latLng.latitude, latLng.longitude);
            }
        });
    }

    @Override
    public void addMarker(double latitude, double longitud, String location) {
        LatLng latLng = new LatLng(latitude, longitud);
        if (mMap != null) {
            if (mMarker != null) {
                mMarker.remove();
            }
            if (!location.isEmpty()) {
                mMarker =  mMap.addMarker(new MarkerOptions().position(latLng)
                        .title(location));
                mPrefecence.showLocation(location);
            }
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void showSetLocation(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadLocation() {
        if (mPresenter == null) {
            mPresenter = new MapsPresenter(this, getContext());
        }
        if (mMapFragment != null) {
            mMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    mMap = googleMap;
                    mPresenter.loadLocation();
                }
            });
        }
    }


    public interface setLocation {
        void showLocation(String location);
    }
}
