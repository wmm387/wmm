package com.wangyuanwmm.wmm.entity;

import java.util.ArrayList;

public class ZhihuDailyNews {

    private String date;
    private ArrayList<Question> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Question> getStories() {
        return stories;
    }

    public class Question {

        private ArrayList<String> images;
        private int type;
        private int id;
        private String title;

        public ArrayList<String> getImages() {
            return images;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }

}
