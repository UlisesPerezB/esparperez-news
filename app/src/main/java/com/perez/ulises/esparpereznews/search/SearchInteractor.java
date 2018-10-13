package com.perez.ulises.esparpereznews.search;

import android.content.Context;

import io.realm.Realm;

public class SearchInteractor implements SearchInterface.ISearchInteractor {

    private SearchInterface.ISearchListener mListener;
    private Context mContext;
    private Realm mRealm;

    public SearchInteractor(SearchInterface.ISearchListener listener,Context context) {
        this.mListener = listener;
        this.mContext = context;
    }

    @Override
    public void getResults() {
        mRealm = Realm.getDefaultInstance();
        
    }

    @Override
    public void getSuggestions(String search) {

    }
}
