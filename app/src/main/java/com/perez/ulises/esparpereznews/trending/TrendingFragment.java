package com.perez.ulises.esparpereznews.trending;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.model.News;
import com.perez.ulises.esparpereznews.splash.ISplash;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

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

    private Realm realm;

    private TrendingInterface.ITrendingPresenter presenter;
    //TODO Se va a dejar de usar el progress dialog y utilizaremos el loader animado
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_fragment, container, false);
        ButterKnife.bind(this, view);
//        realm = Realm.getDefaultInstance();
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
            dialog = new ProgressDialog(getContext());
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
        //TODO Aquí se debe usar el Recycler
        TrendingAdapter adapter;

        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TrendingAdapter(getContext());
        mRecycler.setAdapter(adapter);
        adapter.setValues(news);
    }

    @Override
    public void changeBookmark(boolean isBookmark, int position) {

    }

    @Override
    public void showEmptyState() {
        //TODO Aparecer un textview
        mTvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyState() {
        mTvEmpty.setVisibility(View.GONE);
    }
}
