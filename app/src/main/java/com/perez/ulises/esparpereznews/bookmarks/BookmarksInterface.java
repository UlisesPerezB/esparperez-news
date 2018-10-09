package com.perez.ulises.esparpereznews.bookmarks;

import com.perez.ulises.esparpereznews.model.News;

import java.util.List;

interface BookmarksInterface {

    interface IBookmarksPresenter {
        void getBookmarks();
        void changeBookmark(int position);
    }

    interface IBookmarksInteractor {
        void getBookmarks();
        void changeBookmark(int position);
    }

    interface IBookmarksListener {
        void onBookmarksRetrieved(List<News> bookmarks);
        void onBookmarksChanged();
        void onNoBookmarks();
    }

    interface IBookmarksView {
        void showEmptyState();
        void hideEmptyState();
        void loadList(List<News> bookmarks);
    }

}
