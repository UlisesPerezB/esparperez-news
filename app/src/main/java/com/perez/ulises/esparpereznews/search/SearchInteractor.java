package com.perez.ulises.esparpereznews.search;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.model.News;
import com.perez.ulises.esparpereznews.model.Search;
import com.perez.ulises.esparpereznews.splash.SplashInteractor;
import com.perez.ulises.esparpereznews.utils.IRequest;
import com.perez.ulises.esparpereznews.utils.Util;
import com.perez.ulises.esparpereznews.utils.VolleyRequests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.perez.ulises.esparpereznews.utils.Constants.BING_HEADER;
import static com.perez.ulises.esparpereznews.utils.Constants.BING_SEARCH_URL;
import static com.perez.ulises.esparpereznews.utils.Constants.BING_TOKEN;
import static com.perez.ulises.esparpereznews.utils.Constants.INSERT_WORD;

public class SearchInteractor implements SearchInterface.ISearchInteractor, IRequest.VolleyResponseHandler {

    private SearchInterface.ISearchListener mListener;
    private Context mContext;
    private Realm mRealm;
    private List dictionary;
    private List suggestionsList;
    private List<Search> searchList;
    private List<News> newsList;

    public SearchInteractor(SearchInterface.ISearchListener listener, Context context) {
        this.mListener = listener;
        this.mContext = context;
        this.searchList = new ArrayList<>();
        this.suggestionsList = new ArrayList();
        this.newsList = new ArrayList<>();
        this.dictionary = SplashInteractor.mListSuggestions;
    }

    @Override
    public void getResults() {
        mRealm = Realm.getDefaultInstance();
        String word;
        Search lastSearch = mRealm.where(Search.class).sort("dateSearch", Sort.DESCENDING).findFirst();
        if (lastSearch != null) {
            System.out.println("Busqueda: " + lastSearch.getWord() + " fecha:" + lastSearch.getDateSearch().toString());
            word = lastSearch.getWord();
            requestNews(word);
        } else {
            mListener.onNoNews();
        }
        mRealm.close();
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
            requestNews(word);
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
        }
        mListener.onSuggestionsRetrieved(searchList, suggestionsList);
    }

    @Override
    public void sendSearch(String word) {
        mListener.onRecyclerRetrieved(word);
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

    private void requestNews(String word){
        String sUrl = BING_SEARCH_URL.concat(word).concat("&").concat(Util.urlFormat(mContext));
        VolleyRequests.jsonRequest(mContext, Request.Method.GET, sUrl, BING_HEADER, BING_TOKEN, this);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {

        if (!newsList.isEmpty()) {
            newsList.clear();
        }

        if (jsonObject != null) {
            JSONArray jsonArray = jsonObject.optJSONArray("value");
            for (int i=0; i<jsonArray.length();i++) {
                try {
                    News news = new News(jsonArray.getJSONObject(i));
                    newsList.add(news);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (newsList.isEmpty()) {
            mListener.onNoNews();
        } else {
            mListener.onNewsRetrieved(newsList);
        }
    }

    @Override
    public void onResponse(JSONArray jsonArray) {
        if (!newsList.isEmpty()) {
            newsList.clear();
        }
        if (jsonArray.length() > 0) {
            for (int i = 0; i <= jsonArray.length(); i ++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("value");
                    News mNews = new News(jsonObject);
                    newsList.add(mNews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (newsList.isEmpty()) {
            mListener.onNoNews();
            System.out.print(newsList.get(1));
        } else {
            mListener.onNewsRetrieved(newsList);
        }
    }

    @Override
    public void onError(int error) {
        switch (error) {
            case 500:
                mListener.onNetworkError(mContext.getString(R.string.error_network));
                break;
            default:
                mListener.onNetworkError(mContext.getString(R.string.error_consult));
                break;
        }
    }
}
