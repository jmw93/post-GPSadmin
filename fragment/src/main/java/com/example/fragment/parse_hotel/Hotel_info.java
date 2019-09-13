package com.example.fragment.parse_hotel;

import android.graphics.Bitmap;
import android.media.Image;

public class Hotel_info {
    String title;
    String tel;
    String homepage;
    Bitmap firstimage;
    String mapx;

    public String getMapx() {
        return mapx;
    }

    public void setMapx(String mapx) {
        this.mapx = mapx;
    }

    public String getMapy() {
        return mapy;
    }

    public void setMapy(String mapy) {
        this.mapy = mapy;
    }

    String mapy;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Bitmap getFirstimage() {
        return firstimage;
    }

    public void setFirstimage(Bitmap firstimage) {
        this.firstimage = firstimage;
    }


}
