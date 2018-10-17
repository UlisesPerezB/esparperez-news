package com.perez.ulises.esparpereznews.splash;

import android.content.Context;

public class SplashPresenter implements SplashInterface.ISplashPresenter, SplashInterface.ISplashListener {

    private SplashInterface.ISplashView mSplashView;
    private SplashInterface.ISplashInteractor mSplashInteractor;

    public SplashPresenter(SplashInterface.ISplashView splashView, Context context) {
        this.mSplashView = splashView;
        mSplashInteractor = new SplashInteractor(this, context);
    }

    @Override
    public void getTrendingNews() {
        mSplashInteractor.getTrendingNews();
    }

    @Override
    public void getDictionary() {
        mSplashInteractor.setDictionary();
    }

//    ISplashListener

    @Override
    public void onTrendingNews() {
        mSplashView.showTrendingNews();
    }
}
