package com.perez.ulises.esparpereznews.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.utils.Util;

public class PreferenceInteractor implements PreferenceInterfaces.IPreferenceInteractor {

    private Context mContext;
    private PreferenceInterfaces.IPreferenceListener mListener;
    private SharedPreferences pref;
    private MapsInteractor mapsInteractor;

    public PreferenceInteractor(PreferenceInterfaces.IPreferenceListener listener, Context context) {
        this.mListener = listener;
        this.mContext = context;
        this.mapsInteractor = new MapsInteractor();
        this.pref = mContext.getSharedPreferences(mContext.getString(R.string.preference_key_file), Context.MODE_PRIVATE);
    }

    @Override
    public void saveSettings(boolean safe, int category, int language, int freshness, long since, String location) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(mContext.getString(R.string.pref_key_safe), safe);
        editor.putInt(mContext.getString(R.string.pref_key_category),category);
        editor.putInt(mContext.getString(R.string.pref_key_language), language);
        editor.putInt(mContext.getString(R.string.pref_key_freshness), freshness);
        editor.putLong(mContext.getString(R.string.pref_key_since), since);
        editor.putString(mContext.getString(R.string.pref_key_location),location);
        editor.commit();
        mapsInteractor.saveCountryCode(mContext, location);
    }

    @Override
    public void loadSettings() {
        String sSince = "";
        boolean safe = pref.getBoolean(mContext.getString(R.string.pref_key_safe), false);
        int category = pref.getInt(mContext.getString(R.string.pref_key_category), 0);
        int language = pref.getInt(mContext.getString(R.string.pref_key_language), 0);
        int freshness = pref.getInt(mContext.getString(R.string.pref_key_freshness),0);
        long since = pref.getLong(mContext.getString(R.string.pref_key_since),0);
        if (since != 0) {
            sSince = Util.format(since);
        }
        String location = pref.getString(mContext.getString(R.string.pref_key_location), "");
        mListener.onRetrievedSettings(safe, category, language, freshness, sSince, location );
    }

    @Override
    public void resetSettings() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(mContext.getString(R.string.pref_key_safe), false);
        editor.putInt(mContext.getString(R.string.pref_key_category), 0);
        editor.putInt(mContext.getString(R.string.pref_key_language), 0);
        editor.putInt(mContext.getString(R.string.pref_key_freshness), 0);
        editor.putLong(mContext.getString(R.string.pref_key_since), 0);
        editor.putString(mContext.getString(R.string.pref_key_location), "");
        editor.commit();
        mapsInteractor.saveCountryCode(mContext, "");
        loadSettings();
    }

    public interface onLocationChanged {
        void saveCountryCode(Context context, String location);
    }

}
