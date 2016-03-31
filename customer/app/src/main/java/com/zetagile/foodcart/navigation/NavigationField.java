package com.zetagile.foodcart.navigation;

public class NavigationField {
    String title;
    NavigationItemType type;

    public NavigationField(String title, NavigationItemType type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NavigationItemType getType() {
        return type;
    }

    public void setType(NavigationItemType type) {
        this.type = type;
    }
}
