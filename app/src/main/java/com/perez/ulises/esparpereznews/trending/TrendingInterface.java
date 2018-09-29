package com.perez.ulises.esparpereznews.trending;

import com.perez.ulises.esparpereznews.model.News;

import java.util.List;

public interface TrendingInterface {
    interface ITrendingPresenter {
        void getNews();
        void changeBookmark(int position);
    }
    interface ITrendingInteractor {
        void getNews();
        void changeBookmark(int position);
    }
    interface ITrendingListener {
        void onNetworkError();
        void onNewsRetrieved(List<News> news);
        void onBookmarkChanged(boolean isBookmark, int position);
        void onNoNews();
    }
    interface ITrendingView {
        void showMessage(int resource);
        void showLoadeer(boolean cancelable);
        void hideLoader();
        void loadList(List<News> news);
        void changeBookmark(boolean isBookmark, int position);
        void showEmptyState();
        void hideEmptyState();
    }
}
