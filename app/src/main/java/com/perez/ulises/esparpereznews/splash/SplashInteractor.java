package com.perez.ulises.esparpereznews.splash;

import android.content.Context;
import android.content.res.AssetManager;

import com.perez.ulises.esparpereznews.model.Search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

public class SplashInteractor implements SplashInterface.ISplashInteractor {

    private SplashInterface.ISplashListener mSplashListener;
    private Context mContext;
    private Realm mRealm;
    public static List mListSuggestions = new ArrayList();

    public SplashInteractor(SplashInterface.ISplashListener splashListener, Context context) {
        this.mSplashListener = splashListener;
        this.mContext = context;
    }

    @Override
    public void getTrendingNews() {
        mSplashListener.onTrendingNews();
//        mRealm = Realm.getDefaultInstance();
//        Search lastSearch = mRealm.where(Search.class).sort("dateSearch", Sort.DESCENDING).findFirst();
//        if (lastSearch != null) {
//            System.out.println("Busqueda: " + lastSearch.getWord() + " fecha:" + lastSearch.getDateSearch().toString());
//            mRealm.beginTransaction();
//            mRealm.delete(Search.class);
//            mRealm.commitTransaction();
//            mRealm.close();
//        }
    }

    @Override
    public void setDictionary() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    loadWords();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void loadWords() throws IOException {
        String path = "dictionary.txt";

        AssetManager assetManager = mContext.getAssets();
        InputStream inputStream = assetManager.open(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        try {
            while (((line = reader.readLine()) != null)) {
               mListSuggestions.add(line);
//                System.out.println("Dictionary word: " + line);
            }
        } catch (NullPointerException e2) {
            e2.printStackTrace();
        } catch (StringIndexOutOfBoundsException e3) {
            e3.printStackTrace();
        } finally {
            reader.close();
        }
    }
}

