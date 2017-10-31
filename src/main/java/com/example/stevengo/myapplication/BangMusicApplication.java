package com.example.stevengo.myapplication;

import android.app.Application;

/**
 * Created by StevenGo on 2017/10/26.
 * 自定义的application,保存全局变量
 */

public class BangMusicApplication extends Application {
    private String locationCity;
    private String locationDistrict;
    private double longitude=181;
    private double latitude=91;
    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationDistrict() {
        return locationDistrict;
    }

    public void setLocationDistrict(String locationDistrict) {
        this.locationDistrict = locationDistrict;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
