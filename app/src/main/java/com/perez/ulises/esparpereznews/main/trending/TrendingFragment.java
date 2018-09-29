package com.perez.ulises.esparpereznews.main.trending;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.perez.ulises.esparpereznews.model.News;

import java.util.List;

public class TrendingFragment extends Fragment implements TrendingInterface.ITrendingView {
    private TrendingInterface.ITrendingPresenter presenter;
    //TODO Se va a dejar de usar el progress dialog y utilizaremos el loader animado
    private ProgressDialog dialog;

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null)
            presenter = new TrendingPresenter(this, getContext());
        presenter.getNews();
    }

    @Override
    public void showMessage(int resource) {
        Toast.makeText(getContext(), resource, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadeer(boolean cancelable) {
        if (dialog == null)
            dialog = new ProgressDialog(getContext());
        dialog.setCancelable(cancelable);
        dialog.setMessage("");
        dialog.show();
    }

    @Override
    public void hideLoader() {
        if (dialog != null)
            dialog.dismiss();
    }

    @Override
    public void loadList(List<News> news) {
        //TODO Aquí se debe usar el Recycler
    }

    @Override
    public void changeBookmark(boolean isBookmark, int position) {

    }

    @Override
    public void showEmptyState() {
        //TODO Aparecer un textview
    }

    @Override
    public void hideEmptyState() {

    }
}
