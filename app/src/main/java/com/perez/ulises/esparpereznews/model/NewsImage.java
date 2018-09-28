package com.perez.ulises.esparpereznews.model;

import org.json.JSONException;
import org.json.JSONObject;

public class NewsImage {

    public String imageUrl;

    public NewsImage(JSONObject object) {
        try {
            imageUrl = object.getString("contentUrl");
        } catch (JSONException e) {
            /*
             * Me marcó una excepción aquí
             */
        }
    }
}
