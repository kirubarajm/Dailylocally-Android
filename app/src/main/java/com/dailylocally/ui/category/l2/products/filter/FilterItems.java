package com.dailylocally.ui.category.l2.products.filter;

public class FilterItems {
    String title;
    int id;

    public FilterItems(int id, String title) {
        this.title = title;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}