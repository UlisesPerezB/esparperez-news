package com.perez.ulises.esparpereznews.trending;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.google.gson.JsonArray;
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
import static com.perez.ulises.esparpereznews.utils.Constants.BING_SEARCH_URL;
import static com.perez.ulises.esparpereznews.utils.Constants.BING_TOKEN;
import static com.perez.ulises.esparpereznews.utils.Util.urlFormat;

public class TrendingInteractor implements TrendingInterface.ITrendingInteractor, IRequest.VolleyResponseHandler {
    private TrendingInterface.ITrendingListener listener;
    private Context mContext;
    private List<News> newsList;
    private int mOffset;

    public TrendingInteractor(TrendingInterface.ITrendingListener listener, Context context) {
        this.listener = listener;
        this.mContext = context;
        this.newsList = new ArrayList<>();
    }

    @Override
    public void getNews(int offset) {
        String url = BING_NEWS_URL.concat(urlFormat(mContext, offset));
        this.mOffset = offset;
        Log.i("URL", url);
        if (offset == 0) {
            newsList.clear();
        }
        VolleyRequests.jsonRequest(mContext, Request.Method.GET, url, BING_HEADER, BING_TOKEN, this);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        int listSize = newsList.size();
        int j = listSize - 1;
        if (jsonObject != null) {
            // Como se tiene un solo objeto JSON, se crea un JSONarray a partir de la etiqueta value
            // .opt (JSONArray) devuelve el objeto mapeado con el nombre de la etiqueta
            JSONArray jsonArray = jsonObject.optJSONArray("value");
            // Se buscan todos los objetos JSON de la etiqueta value dentro del JSONarray
            for (int i=0; i<jsonArray.length();i++) {
                try {
                    News news = new News(jsonArray.getJSONObject(i));
                    if (mOffset != 0) {
                        while(j >= 0) {
                            if (news.getUrl().equals(newsList.get(j).getUrl())) {
                                j = listSize - 1;
                                break;
                            }
                            if (j == 0) {
                                newsList.add(news);
                                j = listSize - 1;
                                break;
                            }
                            j--;
                        }
                    } else {
                        newsList.add(news);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if (newsList.isEmpty()) {
            listener.onNoNews();
        } else {
            if (listSize == newsList.size() && mOffset != 0) {
                listener.onNoMoreNews(mContext.getString(R.string.trending_no_more_news));
            } else {
                listener.onNewsRetrieved(newsList, mOffset);
            }
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
        } else {
            listener.onNewsRetrieved(newsList, mOffset);
        }
    }

    @Override
    public void onError(int error) {
        switch (error) {
            case 100:
                listener.onNetworkError("Verifica tu conexión a Internet");
                break;
            case 500:
                listener.onNetworkError(mContext.getString(R.string.error_network_service));
                break;
            default:
                listener.onNetworkError(mContext.getString(R.string.error_consult));
                break;
        }
    }
}
