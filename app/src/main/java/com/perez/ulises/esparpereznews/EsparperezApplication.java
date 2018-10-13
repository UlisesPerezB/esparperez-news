package com.perez.ulises.esparpereznews;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class EsparperezApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(getString(R.string.app_db))
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
