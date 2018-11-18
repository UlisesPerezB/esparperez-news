package com.perez.ulises.esparpereznews.trending;

import android.content.Context;

import com.android.volley.Request;
import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.model.News;
import com.perez.ulises.esparpereznews.utils.IRequest;
import com.perez.ulises.esparpereznews.utils.VolleyRequests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.perez.ulises.esparpereznews.utils.Constants.BING_HEADER;
import static com.perez.ulises.esparpereznews.utils.Constants.BING_NEWS_URL;
import static com.perez.ulises.esparpereznews.utils.Constants.BING_TOKEN;
import static com.perez.ulises.esparpereznews.utils.Util.urlFormat;

public class TrendingInteractor implements TrendingInterface.ITrendingInteractor, IRequest.VolleyResponseHandler {
    private TrendingInterface.ITrendingListener listener;
    private Context mContext;
    private List<News> newsList;

    public TrendingInteractor(TrendingInterface.ITrendingListener listener, Context context) {
        this.listener = listener;
        this.mContext = context;
        this.newsList = new ArrayList<>();
    }

    @Override
    public void getNews() {
        String url = BING_NEWS_URL.concat(urlFormat(mContext));
        VolleyRequests.jsonRequest(mContext, Request.Method.GET, url, BING_HEADER, BING_TOKEN, this);
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
            newsList.clear();
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

    @Override
    public void onError(int error) {
        switch (error) {
            case 500:
                listener.onNetworkError(mContext.getString(R.string.error_network));
                break;
            default:
                listener.onNetworkError(mContext.getString(R.string.error_consult));
                break;
        }
    }
}
