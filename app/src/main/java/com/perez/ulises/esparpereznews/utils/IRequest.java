package com.perez.ulises.esparpereznews.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IRequest {

    interface VolleyResponseHandler {
        void onResponse(JSONObject jsonObject);
        void onResponse(JSONArray jsonArray);
    }

}
