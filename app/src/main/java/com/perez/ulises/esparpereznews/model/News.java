package com.perez.ulises.esparpereznews.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class News extends RealmObject {

    @PrimaryKey
    private String url;
    private String name;
    private String imageUrl;
    private String description;
    private String datePublished;

    public News(JSONObject object) {
        try {
            name = object.getString("name");
            url = object.getString("url");
            imageUrl = object.getJSONObject("image").getJSONObject("thumbnail").getString("contentUrl");
            description = object.getString("description");
            datePublished = object.getString("datePublished");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public News() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }
}