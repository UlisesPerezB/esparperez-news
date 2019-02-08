package com.perez.ulises.esparpereznews.trending;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import butterknife.OnClick;

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
    @BindView(R.id.loader_container)
    ConstraintLayout mLoaderLayout;
    @BindView(R.id.loader)
    ImageView mLoader;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mRefreshLayout;

    private TrendingInterface.ITrendingPresenter presenter;
    private Animation animLoader;
    private int mOffset;
    private RecyclerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_fragment, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        animLoader = AnimationUtils.loadAnimation(getContext(), R.anim.rotation);
        mRefreshLayout.setOnRefreshListener(refreshListener);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new TrendingPresenter(this, getContext());
        mOffset = 0;
        presenter.getNews(mOffset);
    }

    @Override
    public void showErrorMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoader(boolean cancelable) {
        if (mLoaderLayout.getVisibility() == View.GONE) {
            mLoaderLayout.setVisibility(View.VISIBLE);
            mLoader.setAnimation(animLoader);
        }
    }

    @Override
    public void hideLoader() {
        if (mLoaderLayout.getVisibility() == View.VISIBLE) {
            mLoader.clearAnimation();
            mLoaderLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNews(List<News> news) {
        if (mAdapter == null) {
            mRecycler.setHasFixedSize(true);
            mAdapter = new RecyclerAdapter(getContext());
            mRecycler.setAdapter(mAdapter);
            mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecycler.addOnScrollListener(onScrollListener);
        }
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
            mRecycler.setEnabled(true);
            mOffset = 0;
        }
        mAdapter.setValues(news);
    }

    @Override
    public void showMoreNews(List<News> news) {
        mAdapter.setValues(news);
        mRefreshLayout.setEnabled(true);
    }

    @Override
    public void showNoMoreNews(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        mRefreshLayout.setEnabled(true);
    }

    @Override
    public void showEmptyState() {
        mTvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyState() {
        mTvEmpty.setVisibility(View.GONE);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        int y;
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            y = dy;
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LinearLayoutManager lm = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
            int numItems = lm.getItemCount()-1;
            int lastVisible = lm.findLastCompletelyVisibleItemPosition();
            int firstVisible = lm.findFirstVisibleItemPosition();
            boolean endReached = lastVisible == numItems;
            boolean topReached = firstVisible == 0;

            if (newState == recyclerView.SCROLL_STATE_SETTLING) {
                if (topReached && endReached) {
                    mOffset += 10;
                    mRefreshLayout.setEnabled(false);
                    presenter.getNews(mOffset);
                } else {
                    if (endReached && y > 0) {
                        mOffset += 10;
                        presenter.getNews(mOffset);
                    }
                }
            }
        }
    };

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.refreshNews(0);
        }
    };
}
