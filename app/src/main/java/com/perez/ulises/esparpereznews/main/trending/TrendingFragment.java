package com.perez.ulises.esparpereznews.main.trending;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.perez.ulises.esparpereznews.model.News;

import java.util.List;

public class TrendingFragment extends Fragment implements TrendingInterface.ITrendingView {
    @Override
    public void showMessage(int resource) {
        Toast.makeText(getContext(), resource, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadeer(boolean cancelable) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void loadList(List<News> news) {

    }

    @Override
    public void changeBookmark(boolean isBookmark, int position) {

    }
}
