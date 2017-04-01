package com.islavstan.realmexample;


import io.realm.RealmObject;

public class Book extends RealmObject{
   private String title;
    private int page;

    public Book(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Book(String title, int page) {
        this.title = title;
        this.page = page;
    }
}
