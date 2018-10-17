package com.perez.ulises.esparpereznews.search;

import android.content.Context;
import android.util.Log;

import com.perez.ulises.esparpereznews.model.Search;
import com.perez.ulises.esparpereznews.splash.SplashInteractor;

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
    private List dictionary;

    private List<Search> searchList;
    private List suggestionsList;

    public SearchInteractor(SearchInterface.ISearchListener listener,Context context) {
        this.mListener = listener;
        this.mContext = context;
        this.searchList = new ArrayList<>();
        this.suggestionsList = new ArrayList();
        this.dictionary = SplashInteractor.mListSuggestions;
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
            mRealm.close();
            suggestionsList = searchSugggestions(word);

            Log.i("TEST", "Palabras: " + suggestionsList.size());
        }
        mListener.onSuggestionsRetrieved(searchList, suggestionsList);
    }

    private List<String> searchSugggestions(String word) {
        List suggestions = new ArrayList();
        String temp;
        int i = 0;

        do {
            try {
                if (word.length() <= dictionary.get(i).toString().length()) {
                    if (dictionary.get(i).toString().substring(0, word.length()).startsWith(word)) {
                        temp = dictionary.get(i).toString();
                        suggestions.add(temp);
                    }
                }
            } catch (IndexOutOfBoundsException e1) {
                e1.printStackTrace();
            } catch (NullPointerException e2) {
                e2.printStackTrace();
            }
            i++;
        } while (i<dictionary.size());

        return suggestions;
    }

}
