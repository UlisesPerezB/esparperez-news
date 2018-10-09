package com.perez.ulises.esparpereznews.bookmarks;

import android.content.Context;

import com.perez.ulises.esparpereznews.model.News;

import java.util.List;

public class BookmarksPresenter implements BookmarksInterface.IBookmarksPresenter, BookmarksInterface.IBookmarksListener {

    private BookmarksInterface.IBookmarksView mView;
    private Context mContext;
    private BookmarksInterface.IBookmarksInteractor mInteractor;

    public BookmarksPresenter(BookmarksInterface.IBookmarksView view, Context context) {
        this.mView = view;
        this.mContext = context;
        this.mInteractor = new BookmarksInteractor(this, mContext);
    }

    @Override
    public void getBookmarks() {
        mInteractor.getBookmarks();
    }

    @Override
    public void changeBookmark(int position) {
        mInteractor.changeBookmark(position);
    }

    @Override
    public void onBookmarksRetrieved(List<News> bookmarks) {
        mView.hideEmptyState();
        mView.loadList(bookmarks);
    }

    @Override
    public void onBookmarksChanged() {

    }

    @Override
    public void onNoBookmarks() {
        mView.showEmptyState();
    }
}
