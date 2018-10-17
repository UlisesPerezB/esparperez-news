package com.perez.ulises.esparpereznews.splash;

public interface SplashInterface {

//    SplashActivity
    interface ISplashView {
        void showTrendingNews();
    }

//    ISplashPresenter
    interface ISplashPresenter {
        void getTrendingNews();
        void getDictionary();
    }

//    ISplashInteractor
    interface ISplashInteractor {
        void getTrendingNews();
        void setDictionary();
    }

//    ISplashListener
    interface ISplashListener {
        void onTrendingNews();
    }
}
