package com.perez.ulises.esparpereznews.search;

import com.perez.ulises.esparpereznews.model.News;
import com.perez.ulises.esparpereznews.model.Searches;

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
        void onSuggestionsRetrieved(List<Searches> searches, List suggestions);
    }

    interface ISearchView {
        void showEmptyState();
        void hideEmptyState();
        void showSearchSuggestions(List<Searches> searches, List suggestions);
        void showNewsResults(List<News> news);
    }

}
