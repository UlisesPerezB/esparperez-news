package com.perez.ulises.esparpereznews.trending;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.model.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class TrendingAdapter extends RealmRecyclerViewAdapter<News, TrendingAdapter.TrendingViewHolder> {

    List<News> mValues;

    public TrendingAdapter(@Nullable OrderedRealmCollection<News> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new TrendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingAdapter.TrendingViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//      Imagen pendiente
//      holder.newsImage.setImageResource();
        holder.tvNewsHeader.setText(mValues.get(position).getName());
        holder.tvNewsSubHeader.setText(mValues.get(position).getDescription());
        holder.tvNewsUrl.setText(mValues.get(position).getUrl());
        holder.tvNewsDate.setText(mValues.get(position).getDatePublished());
    }

    class TrendingViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public News mItem;

        @BindView(R.id.newsImage)
        ImageView newsImage;
        @BindView(R.id.newsHeader)
        TextView tvNewsHeader;
        @BindView(R.id.newsSubHeader)
        TextView tvNewsSubHeader;
        @BindView(R.id.newsUrl)
        TextView tvNewsUrl;
        @BindView(R.id.newsDate)
        TextView tvNewsDate;

        public TrendingViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, mView);
        }
    }
}
