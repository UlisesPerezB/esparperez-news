package com.perez.ulises.esparpereznews.search;

import android.content.Context;

import com.perez.ulises.esparpereznews.model.News;
import com.perez.ulises.esparpereznews.model.Searches;

import java.util.List;

public class SearchPresenter implements SearchInterface.ISearchPresenter, SearchInterface.ISearchListener{

    private SearchInterface.ISearchView mView;
    private SearchInterface.ISearchInteractor mInteractor;

    public SearchPresenter(SearchInterface.ISearchView view, Context context) {
        this.mView = view;
        this.mInteractor = new SearchInteractor(this, context);
    }

    @Override
    public void getNewsResults() {
        mInteractor.getResults();
    }

    @Override
    public void getSuggestions(String search) {
        mInteractor.getSuggestions(search);
    }

    @Override
    public void updateSearches(String search) {
        mInteractor.updateSearches(search);
    }

    @Override
    public void onNewsRetrieved(List<News> news) {
        mView.showNewsResults(news);
    }

    @Override
    public void onSuggestionsRetrieved(List<Searches> searches, List suggestions) {
        mView.showSearchSuggestions(searches, suggestions);
    }
}
