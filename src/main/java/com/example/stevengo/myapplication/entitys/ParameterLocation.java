package com.example.stevengo.myapplication.entitys;

import retrofit2.Converter;

/**
 * Created by StevenGo on 2017/10/17.
 *
 */

public class ParameterLocation {
    private String key;
    private String location;
    private String poitype;
    private double radius;
    private String extensions;
    private boolean batch;
    private String roadlevel;
    private String sig;
    private String output;
    private String callback;
    private int homeocorp;
    public ParameterLocation(){

    }
    public ParameterLocation(String key,String location ){
        this.key=key;
        this.location=location;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPoitype() {
        return poitype;
    }

    public void setPoitype(String poitype) {
        this.poitype = poitype;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public boolean isBatch() {
        return batch;
    }

    public void setBatch(boolean batch) {
        this.batch = batch;
    }

    public String getRoadlevel() {
        return roadlevel;
    }

    public void setRoadlevel(String roadlevel) {
        this.roadlevel = roadlevel;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public int getHomeocorp() {
        return homeocorp;
    }

    public void setHomeocorp(int homeocorp) {
        this.homeocorp = homeocorp;
    }
}
class Coordinate{
    /**经度*/
    private double longitude;
    /**纬度*/
    private double latitude;
    public Coordinate(){

    }
    public Coordinate(double longitude,double latitude){
        this.longitude=longitude;
        this.latitude=latitude;
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

    @Override
    public String toString() {
        return String.valueOf(longitude)+","+String.valueOf(latitude);
    }
}
