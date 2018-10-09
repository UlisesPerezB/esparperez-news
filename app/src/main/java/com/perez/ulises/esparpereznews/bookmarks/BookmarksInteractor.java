package com.perez.ulises.esparpereznews.bookmarks;

import android.content.Context;
import android.util.Log;

import com.perez.ulises.esparpereznews.model.News;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class BookmarksInteractor implements BookmarksInterface.IBookmarksInteractor {

    private BookmarksInterface.IBookmarksListener mListener;
    private Context mContext;
    private List<News> newsList;

    private Realm realm;

    public BookmarksInteractor(BookmarksInterface.IBookmarksListener listener, Context context) {
        this.mListener = listener;
        this.mContext = context;
        this.newsList = new ArrayList<>();
    }

    @Override
    public void getBookmarks() {
//        realm = Realm.getDefaultInstance();
//        boolean find = realm.where(News.class).findAll() == null;
//        realm.close();

        realm = Realm.getDefaultInstance();
        RealmResults<News> results = realm.where(News.class).findAll();
        if (results.size()>0) {
            newsList.addAll(realm.copyFromRealm(results));
            mListener.onBookmarksRetrieved(newsList);
            Log.i(getClass().getName(), "Sí hay bookmarks: " + results.size());
        } else {
            mListener.onNoBookmarks();
            Log.i(getClass().getName(), "No bookmarks");
        }
        realm.close();
    }

    @Override
    public void changeBookmark(int position) {

    }
}
