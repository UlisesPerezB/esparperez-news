package com.perez.ulises.esparpereznews.trending;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
import io.realm.Realm;

import static com.perez.ulises.esparpereznews.utils.Util.recyclerDate;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TrendingViewHolder> {

    private List<News> mValues;
    private Context mContext;
    private Realm realm;

    public RecyclerAdapter(Context context) {
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
    public void onBindViewHolder(@NonNull final RecyclerAdapter.TrendingViewHolder holder, final int position) {
        final News item = mValues.get(position);

        holder.itemView.setOnClickListener( v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
            mContext.startActivity(browserIntent);
            });

        if (!item.getImageUrl().isEmpty()) {
            Glide
                    .with(mContext)
                    .load(item.getImageUrl())
                    .into(holder.newsImage);
        } else {
            Glide
                    .with(mContext)
                    .load(R.drawable.ic_no_picture)
                    .into(holder.newsImage);
        }

        holder.tvNewsHeader.setText(item.getName());
        holder.tvNewsSubHeader.setText(item.getDescription());
        holder.tvNewsUrl.setText(item.getUrl());
        holder.tvNewsDate.setText(recyclerDate(item.getDatePublished()));

        realm = Realm.getDefaultInstance();
        boolean find = realm.where(News.class).equalTo("url", item.getUrl()).findFirst() != null;
        realm.close();

        holder.imgBookmark.setOnClickListener(v -> {
                realm = Realm.getDefaultInstance();
                News toFind = realm.where(News.class).equalTo("url", item.getUrl()).findFirst();
                realm.beginTransaction();
                if (toFind != null)
                    toFind.deleteFromRealm();
                else
                    realm.insertOrUpdate(item);
                realm.commitTransaction();
                realm.close();
                notifyItemChanged(position);
        });

        int bookmarkColor = ContextCompat.getColor(mContext, find ? R.color.colorPrimary : R.color.colorSecondaryText);
        holder.imgBookmark.setColorFilter(bookmarkColor);
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
        @BindView(R.id.imgBookmark)
        ImageView imgBookmark;

        public TrendingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
