package com.perez.ulises.esparpereznews.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.main.MainActivity;
import com.perez.ulises.esparpereznews.model.News;
import com.perez.ulises.esparpereznews.model.Search;
import com.perez.ulises.esparpereznews.trending.RecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment implements SearchInterface.ISearchView, SearchInterface.ISearchInterface {

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    public SearchFragment (){ }

    @BindView(R.id.search_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.tv_news_empty)
    TextView tvEmpty;

    private SearchPresenter mPresenter;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (mPresenter == null)
            mPresenter = new SearchPresenter(this, getContext());
        mPresenter.getNewsResults();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof RecyclerWord)
            mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void showEmptyState() {
        recyclerView.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(getString(R.string.no_searches));
    }

    @Override
    public void hideEmptyState() {
        tvEmpty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSearchSuggestions(List<Search> searches, List suggestions) {
        SearchAdapter adapter;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchAdapter(getContext(), mPresenter);
        recyclerView.setAdapter(adapter);
        adapter.setValues(searches, suggestions);
    }

    @Override
    public void showNewsResults(List<News> news) {
        RecyclerAdapter adapter;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.setValues(news);
    }

    @Override
    public void showRecyclerWord(String word) {
        if (mainActivity != null){
            mainActivity.setWord(word);
        }
    }

    @Override
    public void searchForWord(String word, int action) {
        mPresenter.getSuggestions(word, action);
    }

    public interface RecyclerWord{
        void setWord(String word);
    }

}
