package com.perez.ulises.esparpereznews.model;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Bookmarks extends RealmObject {

    @PrimaryKey
    private String url;
    private String name;
    private String imageUrl;
    private String description;
    private String datePublished;
    private boolean isBookmark;

    public Bookmarks(JSONObject object) {
        try {
            name = object.getString("name");
            url = object.getString("url");
            imageUrl = object.getJSONObject("image").getJSONObject("thumbnail").getString("contentUrl");
            description = object.getString("description");
            datePublished = object.getString("datePublished");
            isBookmark = false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Bookmarks(String url, String name, String imageUrl, String description, String datePublished, boolean isBookmark) {
        this.url = url;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.datePublished = datePublished;
        this.isBookmark = isBookmark;
    }

    public Bookmarks() {
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

    public boolean isBookmark() {
        return isBookmark;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }

}
