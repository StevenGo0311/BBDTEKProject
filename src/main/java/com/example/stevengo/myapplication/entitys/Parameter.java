package com.example.stevengo.myapplication.entitys;

/**
 * Created by StevenGo on 2017/9/30.
 * 记录参数的实体
 */

public class Parameter {
    /**
     * 关键字
     */
    private String kw;
    /**
     * 页数
     */
    private int pi;
    /**
     * 页大小
     */
    private int pz;

    public Parameter() {

    }

    public Parameter(String kw, int pi, int pz) {
        this.kw = kw;
        this.pi = pi;
        this.pz = pz;
    }

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public int getPi() {
        return pi;
    }

    public void setPi(int pi) {
        this.pi = pi;
    }

    public int getPz() {
        return pz;
    }

    public void setPz(int pz) {
        this.pz = pz;
    }
}
