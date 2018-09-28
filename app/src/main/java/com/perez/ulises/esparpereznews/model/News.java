package com.perez.ulises.esparpereznews.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class News {

    public String name;
    public String url;
//    public String imageUrl;
    public String description;
    public String datePublished;
    public String category;

    public News(JSONObject object) {
        try {
            name = object.getString("name");
            url = object.getString("url");
            //        imageUrl = object.getString("url");
            description = object.getString("description");
            datePublished = object.getString("datePublished");
            category = object.getString("category");
        } catch (JSONException e) {
            /*
             * Me marcó una excepción aquí
             */
        }
    }

}