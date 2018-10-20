package com.perez.ulises.esparpereznews.trending;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private TrendingInterface.ITrendingPresenter presenter;
    //TODO Se va a dejar de usar el progress dialog y utilizaremos el loader animado
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_fragment, container, false);
        ButterKnife.bind(this, view);
        ((SimpleItemAnimator) mRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new TrendingPresenter(this, getContext());
        presenter.getNews();
    }

    @Override
    public void showMessage(int resource) {
        Toast.makeText(getContext(), resource, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadeer(boolean cancelable) {
        if (dialog == null)
            dialog = new ProgressDialog(getContext(), R.style.MyDialogTheme);
        dialog.setCancelable(cancelable);
        dialog.setMessage("");
        dialog.show();
    }

    @Override
    public void hideLoader() {
        if (dialog != null)
            dialog.dismiss();
    }

    @Override
    public void loadList(List<News> news) {
        RecyclerAdapter adapter;
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
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
