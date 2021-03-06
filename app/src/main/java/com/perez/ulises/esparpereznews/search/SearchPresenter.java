package com.perez.ulises.esparpereznews.search;

import android.content.Context;

import com.perez.ulises.esparpereznews.model.News;
import com.perez.ulises.esparpereznews.model.Search;

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
    public void getSuggestions(String search, int action) {
        mInteractor.getSuggestions(search, action);
    }

    @Override
    public void getSearch(String word) {
        mInteractor.sendSearch(word);
    }

    @Override
    public void onNewsRetrieved(List<News> news) {
        mView.hideEmptyState();
        mView.showNewsResults(news);
    }

    @Override
    public void onNetworkError(String error) {
        mView.hideEmptyState();
        mView.showErrorMessage(error);
    }

    @Override
    public void onNoNews() {
        mView.showEmptyState();
    }

    @Override
    public void onSuggestionsRetrieved(List<Search> searches, List suggestions) {
        mView.hideEmptyState();
        mView.showSearchSuggestions(searches, suggestions);
    }

    @Override
    public void onRecyclerRetrieved(String word) {
        mView.showRecyclerWord(word);
    }
}
