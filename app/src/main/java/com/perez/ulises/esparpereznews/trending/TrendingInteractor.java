package com.perez.ulises.esparpereznews.trending;

import android.content.Context;

import com.perez.ulises.esparpereznews.model.News;

import java.util.ArrayList;
import java.util.List;

public class TrendingInteractor implements TrendingInterface.ITrendingInteractor {
    private TrendingInterface.ITrendingListener listener;
    private Context context;

    public TrendingInteractor(TrendingInterface.ITrendingListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void getNews() {
        //TODO: Remover cuando se empiecen a consumir de verdad
        List<News> newsList = new ArrayList<>();
        News newsOne = new News("https://diario.mx/Estado/lorem/",
                "Lorem ipsum Dolor Sit Amet",
                "https://www.bing.com/th?id=ON.EC889370D1F59B9AA0BA7B706CE3443F&pid=News",
                "Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et" +
                        " dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation" +
                        " ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure" +
                        " dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat",
                "2018-02-02",
                false);
        News newsTwo = new News("https://eluniversal.com.mx/opinion/ipsum/",
                "Consectetur adipiscing elit, sed do eiusmod",
                "https://www.bing.com/th?id=ON.EC889370D1F59B9AA0BA7B706CE3443F&pid=News",
                "Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et" +
                        " dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation" +
                        " ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure" +
                        " dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat",
                "2018-02-02",
                false);
        newsList.add(newsOne);
        newsList.add(newsTwo);
        newsList.add(newsOne);
        newsList.add(newsTwo);
        newsList.add(newsOne);
        newsList.add(newsTwo);
        newsList.add(newsOne);
        newsList.add(newsTwo);
        newsList.add(newsOne);
        newsList.add(newsTwo);

//      Test empty
        newsList.clear();
        if (newsList.isEmpty()) {
            listener.onNoNews();
        } else {
            listener.onNewsRetrieved(newsList);
        }
    }

    @Override
    public void changeBookmark(int position) {
        listener.onBookmarkChanged(true, position);
    }
}
