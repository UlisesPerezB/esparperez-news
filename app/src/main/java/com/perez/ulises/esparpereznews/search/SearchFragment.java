package com.perez.ulises.esparpereznews.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.perez.ulises.esparpereznews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchFragment extends Fragment implements SearchInterface.ISearchView {

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    public SearchFragment () { }

    @BindView(R.id.trending_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.edt_search)
    EditText edtSearch;

    @BindView(R.id.tv_news_empty)
    TextView tvEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trending_fragment, container, false);
        ButterKnife.bind(this, view);
        tvTitle.setText(getString(R.string.label_search));

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void showEmptyState() {
        tvEmpty.setText(getString(R.string.text_view_empty));
        tvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyState() {
        tvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showSearchSuggestions() {

    }

    @Override
    public void showNewsResults() {

    }
}
