package com.perez.ulises.esparpereznews.trending;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.model.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder> {

    private List<News> mValues;
    private Context mContext;

    public TrendingAdapter(Context context) {
        mValues = new ArrayList<>();
        this.mContext = context;
    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new TrendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrendingAdapter.TrendingViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
//      Imagen
        Glide
                .with(mContext)
                .load(mValues.get(position).getImageUrl())
                .into(holder.newsImage);
        holder.tvNewsHeader.setText(mValues.get(position).getName());
        holder.tvNewsSubHeader.setText(mValues.get(position).getDescription());
        holder.tvNewsUrl.setText(mValues.get(position).getUrl());
        holder.tvNewsDate.setText(mValues.get(position).getDatePublished());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setValues(List<News> values) {
        mValues = values;
        notifyDataSetChanged();
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
