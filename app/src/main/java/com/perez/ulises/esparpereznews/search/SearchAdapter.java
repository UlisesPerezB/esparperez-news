package com.perez.ulises.esparpereznews.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.model.Search;
import com.perez.ulises.esparpereznews.utils.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Search> mSearches;
    private List mSuggestions;
//    private Context mContext;
//    private Realm realm;
    private SearchInterface.ISearchPresenter mPresenter;

    public SearchAdapter(Context context, SearchInterface.ISearchPresenter presenter) {
        this.mSearches = new ArrayList<>();
        this.mSuggestions = new ArrayList<>();
//        this.mContext = context;
        this.mPresenter = presenter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mSearches.size()) {
            return 0;
        } else
            return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_searches, parent, false);
            return new SearchViewHolder(view);
        } else {
            view =LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_suggestions, parent, false);
            return new SuggestionsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType() == 0) {
            final Search itemSearch = mSearches.get(position);
            SearchViewHolder searchHolder = (SearchViewHolder) holder;
            searchHolder.tvResult.setText(itemSearch.getWord());
            searchHolder.tvDate.setText(Util.format(itemSearch.getDateSearch()));
            searchHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.getSearch(itemSearch.getWord());
                }
            });
        } else {
            SuggestionsViewHolder suggestionsHolder = (SuggestionsViewHolder) holder;
            final String word = mSuggestions.get(position - mSearches.size()).toString();
            suggestionsHolder.tvSuggestionsResult.setText(word);
            suggestionsHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.getSearch(word);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int realCount;
        realCount = mSearches.size() + mSuggestions.size();
        return realCount;
    }

    public void setValues(List<Search> searches, List suggestions) {
        mSearches = searches;
        mSuggestions = suggestions;
        notifyDataSetChanged();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_search_result)
        TextView tvResult;
        @BindView(R.id.tv_search_date)
        TextView tvDate;

        public SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class SuggestionsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_suggestions_result)
        TextView tvSuggestionsResult;
        public SuggestionsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
