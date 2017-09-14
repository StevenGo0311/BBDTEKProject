package com.example.stevengo.myapplication.entitys;

/**
 * Created by StevenGo on 2017/9/13.
 * 音乐的实体，包括名称，拼音和首字母
 */

public class MusicInfo {
    /**音乐名称*/
    private String name;
    /**拼音*/
    private String phoneticize;
    /**首字母*/
    private String inital;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneticize() {
        return phoneticize;
    }

    public void setPhoneticize(String phoneticize) {
        this.phoneticize = phoneticize;
    }

    public String getInital() {
        return inital;
    }

    public void setInital(String inital) {
        this.inital = inital;
    }
}
