package com.perez.ulises.esparpereznews.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.perez.ulises.esparpereznews.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements ISplash.View {

    private ISplash.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_activity);

        mPresenter = new Presenter(this);
        mPresenter.getTrendingNews();
    }

    @Override
    public void showTrendingNews() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
