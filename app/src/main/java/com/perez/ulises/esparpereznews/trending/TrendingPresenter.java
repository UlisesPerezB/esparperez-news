package com.perez.ulises.esparpereznews.trending;

import android.content.Context;

import com.perez.ulises.esparpereznews.model.News;

import java.util.ArrayList;
import java.util.List;

public class TrendingPresenter implements TrendingInterface.ITrendingPresenter, TrendingInterface.ITrendingListener {
    private TrendingInterface.ITrendingInteractor interactor;
    private TrendingInterface.ITrendingView view;

    public TrendingPresenter(TrendingInterface.ITrendingView view, Context context) {
        this.view = view;
        interactor = new TrendingInteractor(this, context);
    }

    @Override
    public void getNews() {
        interactor.getNews();
//        view.showLoadeer(false);
    }

    @Override
    public void changeBookmark(int position) {
        interactor.changeBookmark(position);
    }

    @Override
    public void onNetworkError() {
        view.hideLoader();
        view.showEmptyState();
        view.showMessage(0);
    }

    @Override
    public void onNewsRetrieved(List<News> news) {
//        view.hideLoader();
        view.hideEmptyState();
        view.loadList(news);
    }

    @Override
    public void onBookmarkChanged(boolean isBookmark, int position) {
        view.changeBookmark(isBookmark, position);
    }

    @Override
    public void onNoNews() {
//        view.hideLoader();
        view.showEmptyState();
    }
}
