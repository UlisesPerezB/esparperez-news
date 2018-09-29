package com.perez.ulises.esparpereznews.main;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.perez.ulises.esparpereznews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawerAdapter extends ArrayAdapter<DrawerItem> {

    private Context mContext;
    private int mLayoutId;
    private DrawerItem mDrawerItems[] = null;

    @BindView(R.id.ic_image_drawer)
    ImageView imageViewIcon;

    @BindView(R.id.tv_drawer)
    TextView textViewDrawer;


    public DrawerAdapter(@NonNull Context context, int layoutId, @NonNull DrawerItem[] drawerItems) {
        super(context, layoutId, drawerItems);
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mDrawerItems = drawerItems;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View mView = view;
        ButterKnife.bind(this,mView);
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        mView = inflater.inflate(mLayoutId, parent, false);
        DrawerItem folder = mDrawerItems[position];
        imageViewIcon.setImageResource(folder.mIcon);
        textViewDrawer.setText(folder.mName);
        return mView;
    }
}
