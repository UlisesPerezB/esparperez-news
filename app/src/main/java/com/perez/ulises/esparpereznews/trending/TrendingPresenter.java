package com.perez.ulises.esparpereznews.trending;

import android.content.Context;

import com.perez.ulises.esparpereznews.model.News;

import java.util.List;

public class TrendingPresenter implements TrendingInterface.ITrendingPresenter, TrendingInterface.ITrendingListener {
    private TrendingInterface.ITrendingInteractor interactor;
    private TrendingInterface.ITrendingView view;

    public TrendingPresenter(TrendingInterface.ITrendingView view, Context context) {
        this.view = view;
        interactor = new TrendingInteractor(this, context);
    }

    @Override
    public void getNews(int offset) {
        interactor.getNews(offset);
        view.showLoader(false);
    }

    @Override
    public void refreshNews(int offset) {
        interactor.getNews(offset);
    }

    @Override
    public void onNetworkError(String error) {
        view.hideLoader();
        view.showEmptyState();
        view.showErrorMessage(error);
    }

    @Override
    public void onNewsRetrieved(List<News> news, int offset) {
        view.hideLoader();
        view.hideEmptyState();
        if (offset > 0) {
            view.showMoreNews(news);
        } else {
            view.showNews(news);
        }
    }

    @Override
    public void onNoNews() {
        view.hideLoader();
        view.showEmptyState();
    }

    @Override
    public void onNoMoreNews(String message) {
        view.hideLoader();
        view.showNoMoreNews(message);
    }
}
