package com.perez.ulises.esparpereznews.trending;

import android.content.Context;

import com.android.volley.Request;
import com.perez.ulises.esparpereznews.utils.IRequest;
import com.perez.ulises.esparpereznews.utils.VolleyRequests;
import com.perez.ulises.esparpereznews.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.perez.ulises.esparpereznews.utils.Constants.BING_HEADER;
import static com.perez.ulises.esparpereznews.utils.Constants.BING_SEARCH_URL;
import static com.perez.ulises.esparpereznews.utils.Constants.BING_TOKEN;

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
//        String url = "https://api.cognitive.microsoft.com/bing/v7.0/news/search";
//        String header = "Ocp-Apim-Subscription-Key";
//        String token = "6dc03acb5ff6466687c063d3b857c15f";
        VolleyRequests.jsonRequest(context, Request.Method.GET, BING_SEARCH_URL, BING_HEADER, BING_TOKEN, this);
    }

    @Override
    public void changeBookmark(int position) {
        listener.onBookmarkChanged(true, position);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject != null) {
            // Como se tiene un solo objeto JSON, se crea un JSONarray a partir de la etiqueta value
            // .opt (JSONArray) devuelve el objeto mapeado con el nombre de la etiqueta
            JSONArray jsonArray = jsonObject.optJSONArray("value");
            // Se buscan todos los objetos JSON de la etiqueta value dentro del JSONarray
            for (int i=0; i<jsonArray.length();i++) {
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

    @Override
    public void onResponse(JSONArray jsonArray) {
        if (jsonArray.length() > 0) {

            for (int i = 0; i <= jsonArray.length(); i ++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("value");
                    News mNews = new News(jsonObject);
                    newsList.add(mNews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (newsList.isEmpty()) {
            listener.onNoNews();
            System.out.print(newsList.get(1));
        } else {
            listener.onNewsRetrieved(newsList);
        }
    }
}
