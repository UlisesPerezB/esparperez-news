package com.perez.ulises.esparpereznews.search;

import android.content.Context;
import android.util.Log;

import com.perez.ulises.esparpereznews.model.Search;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.perez.ulises.esparpereznews.utils.Constants.INSERT_WORD;

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
    public void getSuggestions(String word, int action) {
        mRealm = Realm.getDefaultInstance();
        suggestionsList.clear();
        searchList.clear();

        if (action == INSERT_WORD) {
            Search toFind = mRealm.where(Search.class).equalTo("word", word).findFirst();
            if (toFind != null) {
                mRealm.beginTransaction();
                toFind.setDateSearch(Calendar.getInstance().getTime());
                mRealm.commitTransaction();
            } else {
                mRealm.beginTransaction();
                Search search = new Search();
                search.setWord(word);
                search.setDateSearch(Calendar.getInstance().getTime());
                mRealm.insertOrUpdate(search);
                mRealm.commitTransaction();
            }
        }

        if (word.isEmpty()) {
            RealmResults<Search> searchResults = mRealm.where(Search.class).findAll();
            searchList.addAll(mRealm.copyFromRealm(searchResults));
        } else {
            RealmResults<Search> searchResults = mRealm.where(Search.class)
                    .beginsWith("word", word).findAll();
            searchList.addAll(mRealm.copyFromRealm(searchResults));

            Dictionary dictionary = new Dictionary(mContext);
            suggestionsList = dictionary.readLine("dictionary.txt", word);
            Log.i("TEST", "Palabras: " + suggestionsList.size());
        }
        mListener.onSuggestionsRetrieved(searchList, suggestionsList);
    }
}
