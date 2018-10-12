package com.perez.ulises.esparpereznews.search;

import com.perez.ulises.esparpereznews.model.News;

import java.util.List;

public interface SearchInterface {

    interface ISearchPresenter {
        void getNewsResults();
        void getSuggestions(String search);
    }

    interface ISearchInteractor {
        void getResults();
        void getSuggestions(String search);
    }

    interface ISearchListener {
        void onNewsRetrieved(List<News> newsResults);
        void onSuggestionsRetrieved();
    }

    interface ISearchView {
        void showEmptyState();
        void hideEmptyState();
        void showSearchSuggestions();
        void showNewsResults();
    }

}
