package com.perez.ulises.esparpereznews.trending;

import android.content.Context;
import android.util.JsonReader;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.perez.ulises.esparpereznews.Utils.IRequest;
import com.perez.ulises.esparpereznews.Utils.VolleyRequests;
import com.perez.ulises.esparpereznews.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrendingInteractor implements TrendingInterface.ITrendingInteractor, IRequest.VolleyResponseHandler {
    private TrendingInterface.ITrendingListener listener;
    private Context context;
    private List<News> newsList;
    

    public TrendingInteractor(TrendingInterface.ITrendingListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        this.newsList = new ArrayList<>();
    }

    @Override
    public void getNews() {
        String url = "https://api.cognitive.microsoft.com/bing/v7.0/news/search";
        String header = "Ocp-Apim-Subscription-Key";
        String token = "6dc03acb5ff6466687c063d3b857c15f";
        VolleyRequests.jsonRequest(context, Request.Method.GET, url, header, token, this);

//        News newsOne = new News("https://diario.mx/Estado/lorem/",
//                "Lorem ipsum Dolor Sit Amet",
//                "https://www.bing.com/th?id=ON.EC889370D1F59B9AA0BA7B706CE3443F&pid=News",
//                "Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et" +
//                        " dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation" +
//                        " ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure" +
//                        " dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat",
//                "2018-02-02",
//                false);
//        News newsTwo = new News("https://eluniversal.com.mx/opinion/ipsum/",
//                "Consectetur adipiscing elit, sed do eiusmod",
//                "https://www.bing.com/th?id=ON.EC889370D1F59B9AA0BA7B706CE3443F&pid=News",
//                "Consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et" +
//                        " dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation" +
//                        " ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure" +
//                        " dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat",
//                "2018-02-02",
//                false);
//        newsList.add(newsOne);
//        newsList.add(newsTwo);
//        newsList.add(newsOne);
//        newsList.add(newsTwo);
//        newsList.add(newsOne);
//        newsList.add(newsTwo);
//        newsList.add(newsOne);
//        newsList.add(newsTwo);
//        newsList.add(newsOne);
//        newsList.add(newsTwo);

//      Test empty


    }

    @Override
    public void changeBookmark(int position) {
        listener.onBookmarkChanged(true, position);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject != null) {
            News news = new News(jsonObject);
            newsList.add(news);
        }
        if (newsList.isEmpty()) {
            listener.onNoNews();
        } else {
            listener.onNewsRetrieved(newsList);
        }
    }

    @Override
    public void onResponse(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {
            for (int i = 0; i <= jsonArray.length(); i ++) {
                try {
                    News news = new News(jsonArray.getJSONObject(i));
                    newsList.add(news);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (newsList.isEmpty()) {
            listener.onNoNews();
        } else {
            listener.onNewsRetrieved(newsList);
        }
    }
}
