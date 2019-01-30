package com.perez.ulises.esparpereznews.trending;

import android.app.ProgressDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.model.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrendingFragment extends Fragment implements TrendingInterface.ITrendingView {

    public TrendingFragment() { }

    public static TrendingFragment newInstance() {
        TrendingFragment fragment = new TrendingFragment();
        return fragment;
    }

    @BindView(R.id.tv_news_empty)
    TextView mTvEmpty;
    @BindView(R.id.trending_recycler)
    RecyclerView mRecycler;
    @BindView(R.id.loader)
    ImageView mLoader;

    private TrendingInterface.ITrendingPresenter presenter;
    //TODO Se va a dejar de usar el progress dialog y utilizaremos el loader animado
//    private ProgressDialog dialog;
//    private AnimationDrawable animLoader;
    private Animation animLoader;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_fragment, container, false);
        ButterKnife.bind(this, view);
        ((SimpleItemAnimator) mRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        animLoader = AnimationUtils.loadAnimation(getContext(), R.anim.rotation);

//        mLoader.setBackgroundResource(R.drawable.change_loader);
//        animLoader = (AnimationDrawable) mLoader.getBackground();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new TrendingPresenter(this, getContext());
        presenter.getNews();
    }

    @Override
    public void showErrorMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoader(boolean cancelable) {
//        if (dialog == null)
//            dialog = new ProgressDialog(getContext(), R.style.MyDialogTheme);
//        dialog.setCancelable(cancelable);
//        dialog.setMessage("");
//        dialog.show();
// Metodo list drawable
//        if (mLoader.getVisibility() == View.GONE) {
//            mLoader.setVisibility(View.VISIBLE);
//            animLoader.start();
//            Log.d("LOADER","Start Loader");
//        }
        if (mLoader.getVisibility() == View.GONE) {
            mLoader.setVisibility(View.VISIBLE);
            mLoader.setAnimation(animLoader);
            Log.d("LOADER","Start Loader");
        }
    }

    @Override
    public void hideLoader() {
//        if (dialog != null)
//            dialog.dismiss();
//        if (mLoader.getVisibility() == View.VISIBLE) {
//            animLoader.stop();
//            mLoader.setVisibility(View.GONE);
//            Log.d("LOADER","Stop Loader");
//        }

        if (mLoader.getVisibility() == View.VISIBLE) {
            mLoader.clearAnimation();
            mLoader.setVisibility(View.GONE);
            Log.d("LOADER","Stop Loader");
        }
    }

    @Override
    public void loadList(List<News> news) {
        RecyclerAdapter adapter;
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setNestedScrollingEnabled(false);
        adapter = new RecyclerAdapter(getContext());
        mRecycler.setAdapter(adapter);
        adapter.setValues(news);
    }

    @Override
    public void changeBookmark(boolean isBookmark, int position) {

    }

    @Override
    public void showEmptyState() {
        mTvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyState() {
        mTvEmpty.setVisibility(View.GONE);
    }
}
