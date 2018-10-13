package com.perez.ulises.esparpereznews.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.model.Searches;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Searches> mSearches;
    private List mSuggestions;
    private Context mContext;
    private Realm realm;

    public SearchAdapter(Context context) {
        mSearches = new ArrayList<>();
        mSuggestions = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position <= mSearches.size()) {
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
        } else {
            view =LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_suggestions, parent, false);
        }
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == 0) {
            final Searches itemSearches = mSearches.get(position);
            SearchViewHolder searchHolder = (SearchViewHolder) holder;
            searchHolder.tvResult.setText(itemSearches.getWord());
            searchHolder.tvDate.setText(itemSearches.getDateSearch().toString());
            searchHolder.btnDeleteSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "DELETED", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            SuggestionsViewHolder suggestionsHolder = (SuggestionsViewHolder) holder;
            suggestionsHolder.tvSuggestionsResult.setText(mSuggestions.get(position).toString());
        }
    }

    @Override
    public int getItemCount() {
        int realCount;
        realCount = mSearches.size() + mSuggestions.size();
        return realCount;
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

        @BindView(R.id.im_search_delete)
        ImageView btnDeleteSearch;

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
