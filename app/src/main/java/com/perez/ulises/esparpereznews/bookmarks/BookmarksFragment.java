package com.perez.ulises.esparpereznews.bookmarks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.model.News;
import com.perez.ulises.esparpereznews.trending.RecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookmarksFragment extends Fragment implements BookmarksInterface.IBookmarksView {

    public BookmarksFragment() { }

    public static BookmarksFragment newInstance() {
        BookmarksFragment fragment = new BookmarksFragment();
        return fragment;
    }

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_news_empty)
    TextView tvEmpty;

    @BindView(R.id.trending_recycler)
    RecyclerView mRecycler;

    private BookmarksInterface.IBookmarksPresenter mPresenter;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.trending_fragment, container, false);
        ButterKnife.bind(this, view);
        tvTitle.setText(getString(R.string.label_bookmarks));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter == null){
            mPresenter = new BookmarksPresenter(this, getContext());
        }
        mPresenter.getBookmarks();
    }

    @Override
    public void showEmptyState() {
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(R.string.no_bookmarks);
    }

    @Override
    public void hideEmptyState() {
        tvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void loadList(List<News> bookmarks) {
        RecyclerAdapter adapter;
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerAdapter(getContext());
        mRecycler.setAdapter(adapter);
        adapter.setValues(bookmarks);
    }
}
