package com.perez.ulises.esparpereznews.search;

import android.content.Context;
import android.util.Log;

import com.perez.ulises.esparpereznews.model.Searches;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SearchInteractor implements SearchInterface.ISearchInteractor {

    private SearchInterface.ISearchListener mListener;
    private Context mContext;
    private Realm mRealm;

    private List<Searches> searchesList;
    private List suggestionsList;

    public SearchInteractor(SearchInterface.ISearchListener listener,Context context) {
        this.mListener = listener;
        this.mContext = context;
        this.searchesList = new ArrayList<>();
        this.suggestionsList = new ArrayList();
    }

    @Override
    public void getResults() {
        mRealm = Realm.getDefaultInstance();

    }

    @Override
    public void getSuggestions(String search) {
        mRealm = Realm.getDefaultInstance();
        RealmResults<Searches> searchesResults = mRealm.where(Searches.class)
                .beginsWith("word", search).findAll();
        searchesList.addAll(mRealm.copyFromRealm(searchesResults));
        if (searchesList.size() > 0) {
            for (int i = 0; i < searchesList.size(); i++) {
                suggestionsList.add(i, searchesList.get(i).getWord());
                System.out.println("En posición " + i + ": "+ suggestionsList.get(i));
            }
        }
        mRealm.close();
        Log.i("INTERACTOR", "PALABRA: " + search);
        mListener.onSuggestionsRetrieved(searchesResults, suggestionsList);
    }

    @Override
    public void updateSearches(String word) {
        Searches search = new Searches();
        search.setWord(word);
        search.setDateSearch(Calendar.getInstance().getTime());
        Log.i("INTERACTOR_UPDATE", "PALABRA: " + word);
        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        mRealm.insertOrUpdate(search);
        mRealm.commitTransaction();
        mRealm.close();
    }
}
