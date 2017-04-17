package com.wangyuanwmm.wmm.entity;

import java.util.ArrayList;

public class ZhihuDailyStory {

    private String body;
    private String title;
    private String image;
    private String share_url;
    private int type;
    private int id;

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
