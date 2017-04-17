package com.wangyuanwmm.wmm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DoubanMomentStory {

    private String short_url;
    @SerializedName("abstract")
    private int id;
    private String original_url;
    private String content;
    private String type;
    private ArrayList<DoubanMomentNews.posts.thumbs> photos;
    private String url;
    private String title;

    public String getShort_url() {
        return short_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_url() {
        return original_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<DoubanMomentNews.posts.thumbs> getPhotos() {
        return photos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
