package com.example.stevengo.myapplication.entitys;

/**
 * @author StevenGo
 * 定义bean,封装ListviewItem中的数据
 */

public class ListItemContentEntity {
    /**姓名*/
    private String name;
    /**描述*/
    private String describe;
    /**头像Id*/
    private int imageIconIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getImageIconIds() {
        return imageIconIds;
    }

    public void setImageIconIds(int imageIconIds) {
        this.imageIconIds = imageIconIds;
    }
}
