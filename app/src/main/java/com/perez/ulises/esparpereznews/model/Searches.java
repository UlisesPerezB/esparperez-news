package com.perez.ulises.esparpereznews.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Searches extends RealmObject {

    @PrimaryKey
    private String search;
    private Date dateSearch;

    public Searches() {
    }

    public Searches(String search, Date dateSearch) {
        this.search = search;
        this.dateSearch = dateSearch;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Date getDateSearch() {
        return dateSearch;
    }

    public void setDateSearch(Date dateSearch) {
        this.dateSearch = dateSearch;
    }

}
