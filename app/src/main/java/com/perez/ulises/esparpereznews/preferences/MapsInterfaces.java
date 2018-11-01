package com.perez.ulises.esparpereznews.preferences;

public interface MapsInterfaces {

    interface IMapsPresenter {
        void loadLocation();
        void saveLocation(double latitude, double longitud);
    }

    interface IMapsInteractor {
        void loadLocation();
        void saveLocation(double latitude, double longitud);
    }

    interface IMapsListener {
        void onSavedLocation(double latitude, double longitud, String location);
        void onNoLocationSaved(String message);
    }

    interface IMapsView {
        void addMarker(double latitude, double longitud, String location);
        void showSetLocation(String message);
    }
}
