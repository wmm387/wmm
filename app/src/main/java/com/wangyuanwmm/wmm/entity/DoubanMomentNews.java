package com.wangyuanwmm.wmm.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DoubanMomentNews {

    private ArrayList<posts> posts;
    private String date;

    public ArrayList<DoubanMomentNews.posts> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<DoubanMomentNews.posts> posts) {
        this.posts = posts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public class posts {

        private String published_time;
        private String url;
        @SerializedName("abstract")
        private String date;
        private ArrayList<thumbs> thumbs;
        private String title;
        private String type;
        private int id;

        public class thumbs {
            medium medium;
            String tag_name;
            int id;

            public DoubanMomentNews.posts.medium getMedium() {
                return medium;
            }

            public String getTag_name() {
                return tag_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public class medium {
            String url;
            int width;
            int height;

            public String getUrl() {
                return url;
            }
        }

        public String getPublished_time() {
            return published_time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public ArrayList<thumbs> getThumbs() {
            return thumbs;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
