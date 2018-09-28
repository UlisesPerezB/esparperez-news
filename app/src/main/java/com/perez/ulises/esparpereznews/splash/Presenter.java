package com.perez.ulises.esparpereznews.splash;

public class Presenter implements ISplash.Presenter, ISplash.Listener{

    private ISplash.View mView;
    private ISplash.Interactor mInteractor;
    public Presenter(ISplash.View view) {
        this.mView = view;
        mInteractor = new Interactor(this);
    }

    @Override
    public void getTrendingNews() {
        mInteractor.getTrendingNews();
    }

//    Listener

    @Override
    public void onTrendingNews() {
        mView.showTrendingNews();
    }
}
