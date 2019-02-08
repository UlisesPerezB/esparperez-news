package com.perez.ulises.esparpereznews.trending;

import com.perez.ulises.esparpereznews.model.News;

import java.util.List;

public interface TrendingInterface {
    interface ITrendingPresenter {
        void getNews(int offset);
        void refreshNews(int offset);
    }
    interface ITrendingInteractor {
        void getNews(int offset);
    }
    interface ITrendingListener {
        void onNetworkError(String error);
        void onNewsRetrieved(List<News> news, int offset);
        void onNoNews();
        void onNoMoreNews(String message);
    }
    interface ITrendingView {
        void showErrorMessage(String error);
        void showLoader(boolean cancelable);
        void hideLoader();
        void showNews(List<News> news);
        void showMoreNews(List<News> news);
        void showNoMoreNews(String message);
        void showEmptyState();
        void hideEmptyState();
    }
}
