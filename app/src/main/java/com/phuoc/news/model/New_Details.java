package com.phuoc.news.model;

import java.io.Serializable;

public class New_Details implements Serializable {
    private String title;
    private String desc;

    private String image;

    private String release_date;
    private String linkNews;

    public String getLinkNews() {
        return linkNews;
    }

    public void setLinkNews(String linkNews) {
        this.linkNews = linkNews;
    }

    public New_Details(String title, String desc, String image, String release_date, String linkNews) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.release_date = release_date;
        this.linkNews = linkNews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
