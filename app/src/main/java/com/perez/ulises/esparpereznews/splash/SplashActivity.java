package com.perez.ulises.esparpereznews.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.perez.ulises.esparpereznews.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements SplashInterface.ISplashView {

    private SplashInterface.ISplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mSplashPresenter = new SplashPresenter(this, getApplicationContext());
        mSplashPresenter.getTrendingNews();
        mSplashPresenter.getDictionary();
    }

    @Override
    public void showTrendingNews() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
