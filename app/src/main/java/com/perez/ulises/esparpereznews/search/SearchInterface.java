package com.perez.ulises.esparpereznews.search;

import com.perez.ulises.esparpereznews.model.News;
import com.perez.ulises.esparpereznews.model.Search;

import java.util.List;

public interface SearchInterface {

    interface ISearchPresenter {
        void getNewsResults();
        void getSuggestions(String search, int action);
        void getSearch(String word);
    }

    interface ISearchInteractor {
        void getResults();
        void getSuggestions(String search, int action);
        void sendSearch(String word);
    }

    interface ISearchListener {
        void onNewsRetrieved(List<News> newsResults);
        void onNetworkError(String error);
        void onNoNews();
        void onSuggestionsRetrieved(List<Search> searches, List suggestions);
        void onRecyclerRetrieved(String word);
    }

    interface ISearchView {
        void showEmptyState();
        void hideEmptyState();
        void showErrorMessage(String error);
        void showSearchSuggestions(List<Search> searches, List suggestions);
        void showNewsResults(List<News> news);
        void showRecyclerWord(String word);
    }

    interface ISearchInterface {
        void searchForWord(String word, int action);
    }

    interface ISearchAdapterInterface {
        void setRecyclerText(String word);
    }

}
