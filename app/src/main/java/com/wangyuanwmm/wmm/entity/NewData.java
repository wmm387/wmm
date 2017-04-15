package com.wangyuanwmm.wmm.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/15.
 */

public class NewData {

    private ArrayList<list> list;

    public ArrayList<NewData.list> getList() {
        return list;
    }

    public void setList(ArrayList<NewData.list> list) {
        this.list = list;
    }

    public class list {

        //标题
        private String title;
        //出处
        private String source;

        //图片的url
        private String imgUrl;

        //原文地址
        private String url;

        private double tiem;

        public double getTiem() {
            return tiem;
        }

        public void setTiem(double tiem) {
            this.tiem = tiem;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }


}
