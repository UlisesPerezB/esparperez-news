package com.perez.ulises.esparpereznews.model.dao;

import com.perez.ulises.esparpereznews.model.Search;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.exceptions.RealmException;

public class SearchDao {

    /*
     * To be continue
     */

    public void insertOrUpdateSearchList(final List<Search> searchList) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            // Usando una expresión lambda
            realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(searchList));
        } catch (RealmException e) {
            e.printStackTrace();
        } finally {
            if (realm != null){
                realm.close();
            }
        }
    }

    public List<Search> getSearchList() {
        Realm realm = null;
        List<Search> searchList = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(inRealm -> {
                final RealmResults<Search> searches = inRealm.where(Search.class).sort("dateSearch", Sort.DESCENDING).findAll();
                searchList.addAll(inRealm.copyFromRealm(searches));
            });
        } catch (RealmException e) {
            e.printStackTrace();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return searchList;
    }

}
