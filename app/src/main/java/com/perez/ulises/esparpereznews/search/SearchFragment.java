package com.perez.ulises.esparpereznews.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.model.News;
import com.perez.ulises.esparpereznews.model.Searches;
import com.perez.ulises.esparpereznews.trending.RecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchFragment extends Fragment implements SearchInterface.ISearchView {

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    public SearchFragment () { }

    @BindView(R.id.trending_recycler)
    RecyclerView recyclerNews;

    @BindView(R.id.search_recycler)
    RecyclerView recyclerSearch;

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
    public void showSearchSuggestions(List<Searches> searches, List suggestions) {
        SearchAdapter adapter;
        recyclerSearch.setHasFixedSize(true);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchAdapter(getContext());
        recyclerSearch.setAdapter(adapter);
        adapter.setValues(searches, suggestions);
    }

    @Override
    public void showNewsResults(List<News> news) {
        RecyclerAdapter adapter;
        recyclerNews.setHasFixedSize(true);
        recyclerNews.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerAdapter(getContext());
        recyclerNews.setAdapter(adapter);
        adapter.setValues(news);
    }

}
