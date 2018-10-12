package com.perez.ulises.esparpereznews.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.model.Searches;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Searches> mSearches;
    private List mSuggestions;
    private Context mContext;
    private Realm realm;

    public SearchAdapter(Context context) {
        mSearches = new ArrayList<>();
        mSuggestions = new ArrayList<>();
        this.mContext = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_searches, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder holder, final int position) {
        final Searches item = mSearches.get(position);


    }

    @Override
    public int getItemCount() {
        return mSearches.size();
    }

    public void setValues(List<Searches> searches, List suggestions) {
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
}
