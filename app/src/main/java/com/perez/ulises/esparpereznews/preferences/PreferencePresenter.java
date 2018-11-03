package com.perez.ulises.esparpereznews.preferences;


import android.content.Context;

public class PreferencePresenter implements PreferenceInterfaces.IPreferencePresenter, PreferenceInterfaces.IPreferenceListener {

    private PreferenceInterfaces.IPreferenceView mView;
    private PreferenceInterfaces.IPreferenceInteractor mInteractor;

    public PreferencePresenter(PreferenceInterfaces.IPreferenceView view, Context context) {
        this.mView = view;
        this.mInteractor = new PreferenceInteractor(this, context);
    }

    @Override
    public void saveSettings(boolean safe, int category, int language, int freshness, long since, String location) {
        mInteractor.saveSettings(safe, category, language, freshness, since, location);
    }

    @Override
    public void resetSettings() {
        mInteractor.resetSettings();
    }

    @Override
    public void loadSettings() {
        mInteractor.loadSettings();
    }

    @Override
    public void onRetrievedSettings(boolean safe, int category, int language, int freshness, String since, String location) {
        mView.showSettings(safe, category, language, freshness, since, location);
    }
}
