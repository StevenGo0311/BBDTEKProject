package com.example.stevengo.myapplication.entitys;

import java.io.Serializable;

/**
 * Created by StevenGo on 2017/9/13.
 * 音乐的实体，包括音乐名和歌手
 */

public class MusicInfo implements Serializable{
    /**音乐名称*/
    private String name;
    /**歌手*/
    private String singer;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
