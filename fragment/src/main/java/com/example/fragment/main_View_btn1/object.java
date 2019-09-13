package com.example.fragment.main_View_btn1;

public class object {
    int imgpath;
    String title;
    String subtitle;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    String URL;
    public void setImgpath(int imgpath) {
        this.imgpath = imgpath;
    }

    public int getImgpath() {
        return imgpath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }



    public object(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;

    }
}