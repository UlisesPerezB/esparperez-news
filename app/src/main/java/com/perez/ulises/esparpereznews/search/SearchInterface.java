package com.perez.ulises.esparpereznews.search;

import com.perez.ulises.esparpereznews.model.News;
import com.perez.ulises.esparpereznews.model.Search;

import java.util.List;

public interface SearchInterface {

    interface ISearchPresenter {
        void getNewsResults();
        void getSuggestions(String search);
        void updateSearches(String search);
    }

    interface ISearchInteractor {
        void getResults();
        void getSuggestions(String search);
        void updateSearches(String search);
    }

    interface ISearchListener {
        void onNewsRetrieved(List<News> newsResults);
        void onSuggestionsRetrieved(List<Search> searches, List suggestions);
    }

    interface ISearchView {
        void showEmptyState();
        void hideEmptyState();
        void showSearchSuggestions(List<Search> searches, List suggestions);
        void showNewsResults(List<News> news);
    }

    interface ISearchInterface {
        void searchForWord(String word);
        void addNewSearch(String word);

    }

}
