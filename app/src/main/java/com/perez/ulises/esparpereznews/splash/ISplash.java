package com.perez.ulises.esparpereznews.splash;

public interface ISplash {

//    SplashActivity
    interface View {
        void showTrendingNews();
    }

//    Presenter
    interface Presenter {
        void getTrendingNews();

    }

//    Interactor
    interface Interactor {
        void getTrendingNews();
    }

//    Listener
    interface Listener {
        void onTrendingNews();
    }
}
