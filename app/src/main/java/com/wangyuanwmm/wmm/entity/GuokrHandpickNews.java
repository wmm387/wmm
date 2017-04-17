package com.wangyuanwmm.wmm.entity;

import java.util.ArrayList;

public class GuokrHandpickNews {

    private ArrayList<result> result;

    public ArrayList<GuokrHandpickNews.result> getResult() {
        return result;
    }

    public void setResult(ArrayList<GuokrHandpickNews.result> result) {
        this.result = result;
    }

    public class result {
        private int id;
        private String style;
        private String title;
        private String headline_img_tb;
        private double date_picked;
        private String headline_img;
        private String source;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHeadline_img_tb() {
            return headline_img_tb;
        }

        public double getDate_picked() {
            return date_picked;
        }

        public String getHeadline_img() {
            return headline_img;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

}
