package com.perez.ulises.esparpereznews.preferences;

import android.content.Context;

public class MapsPresenter implements MapsInterfaces.IMapsPresenter, MapsInterfaces.IMapsListener {

    private MapsInterfaces.IMapsInteractor mInteractor;
    private MapsInterfaces.IMapsView mView;

    public MapsPresenter(MapsInterfaces.IMapsView view, Context context) {
        this.mInteractor = new MapsInteractor(this, context);
        this.mView = view;
    }

    @Override
    public void loadLocation() {

    }

    @Override
    public void saveLocation(double latitude, double longitud) {
        mInteractor.saveLocation(latitude, longitud);
    }

    @Override
    public void onRetrievedLocation() {

    }

    @Override
    public void onSavedLocation(double latitude, double longitud, String location) {
        mView.addMarker(latitude, longitud, location);
    }
}
