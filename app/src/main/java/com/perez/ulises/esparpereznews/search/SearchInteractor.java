package com.perez.ulises.esparpereznews.search;

import android.content.Context;
import android.util.Log;

import com.perez.ulises.esparpereznews.model.Search;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SearchInteractor implements SearchInterface.ISearchInteractor {

    private SearchInterface.ISearchListener mListener;
    private Context mContext;
    private Realm mRealm;

    private List<Search> searchList;
    private List suggestionsList;

    public SearchInteractor(SearchInterface.ISearchListener listener,Context context) {
        this.mListener = listener;
        this.mContext = context;
        this.searchList = new ArrayList<>();
        this.suggestionsList = new ArrayList();
    }

    @Override
    public void getResults() {
        mRealm = Realm.getDefaultInstance();

    }

    @Override
    public void getSuggestions(String search) {
        mRealm = Realm.getDefaultInstance();
        if (!search.isEmpty()) {
            RealmResults<Search> searchResults = mRealm.where(Search.class)
                    .beginsWith("word", search).findAll();
            searchList.addAll(mRealm.copyFromRealm(searchResults));
            if (!searchList.isEmpty()) {
                for (int i = 0; i < searchList.size(); i++) {
                    suggestionsList.add(i, searchList.get(i).getWord());
                    System.out.println("En posición " + i + ": "+ suggestionsList.get(i));
                }
            }
        } else {
            RealmResults<Search> searchResults = mRealm.where(Search.class).findAll();
            searchList.addAll(mRealm.copyFromRealm(searchResults));
        }
        mRealm.close();
        Log.i("INTERACTOR", "PALABRA: " + search);
        mListener.onSuggestionsRetrieved(searchList, suggestionsList);
    }

    @Override
    public void updateSearches(String word) {
        if (!word.isEmpty()) {
            Search search = new Search();
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
}
