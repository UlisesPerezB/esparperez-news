package com.perez.ulises.esparpereznews.preferences;

public interface PreferenceInterfaces {

    interface IPreferencePresenter {
        void saveSettings(boolean safe, int category, int language, int freshness, long since, String location);
        void resetSettings();
        void loadSettings();
    }

    interface IPreferenceInteractor {
        void saveSettings(boolean safe, int category, int language, int freshness, long since, String location);
        void loadSettings();
        void resetSettings();
    }

    interface IPreferenceListener {
        void onRetrievedSettings(boolean safe, int category, int language, int freshness, String since, String location);
    }

    interface IPreferenceView {
        void showSettings(boolean safe, int category, int language, int freshness, String since, String location);
    }

}
