package com.perez.ulises.esparpereznews.splash;


import android.os.Handler;

public class Interactor implements ISplash.Interactor{

    private ISplash.Listener mListener;

    public Interactor(ISplash.Listener listener) {
        this.mListener = listener;
    }

    @Override
    public void getTrendingNews() {
//        int iTime = 1000;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mListener.onTrendingNews();
//            }
//        }, iTime);

        mListener.onTrendingNews();
    }
}
