package com.zetagile.foodcart.dto.facebook;

import com.zetagile.foodcart.dto.FeedBack;


public class FacebookFeeds extends FeedBack {

    protected String commenter;

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

}
