package com.perez.ulises.esparpereznews.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Searches extends RealmObject {

    @PrimaryKey
    private String word;
    private Date dateSearch;

    public Searches() {
    }

    public Searches(String word, Date dateSearch) {
        this.word = word;
        this.dateSearch = dateSearch;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Date getDateSearch() {
        return dateSearch;
    }

    public void setDateSearch(Date dateSearch) {
        this.dateSearch = dateSearch;
    }

}
