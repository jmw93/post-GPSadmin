package com.example.fragment.parse_Tour;

public class Infodata {

    String mapx;
    String mapy;
    String overview;
    String chkbabycarriage;
    String chkpet;
    String infocenter;
    String parking;

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    @Override
    public String toString() {
        return "Infodata{" +
                "mapx='" + mapx + '\'' +
                ", mapy='" + mapy + '\'' +
                ", overview='" + overview + '\'' +
                ", chkbabycarriage='" + chkbabycarriage + '\'' +
                ", chkpet='" + chkpet + '\'' +
                ", infocenter='" + infocenter + '\'' +
                ", parking='" + parking + '\'' +
                ", restdate='" + restdate + '\'' +
                '}';
    }

    String restdate;

    public String getChkbabycarriage() {
        return chkbabycarriage;
    }

    public void setChkbabycarriage(String chkbabycarriage) {
        this.chkbabycarriage = chkbabycarriage;
    }

    public String getChkpet() {
        return chkpet;
    }

    public void setChkpet(String chkpet) {
        this.chkpet = chkpet;
    }

    public String getInfocenter() {
        return infocenter;
    }

    public void setInfocenter(String infocenter) {
        this.infocenter = infocenter;
    }

    public String getRestdate() {
        return restdate;
    }

    public void setRestdate(String restdate) {
        this.restdate = restdate;
    }

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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

}
