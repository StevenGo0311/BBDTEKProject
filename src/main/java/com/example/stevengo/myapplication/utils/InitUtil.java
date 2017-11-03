package com.example.stevengo.myapplication.utils;

import android.content.Context;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.entitys.ListItemContentEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author StevenGo
 *         用于初始化的工具类，主要包括对首页显示数据的初始化
 */
public class InitUtil {
    /**
     * 轮播图中的图片id
     */
    private int[] imageSlideIds;
    /**
     * 列表项
     */
    private List<ListItemContentEntity> listItemContent;
    /**
     * 列表中的头像
     */
    private int[] iconIds;
    /**
     * 准备列表项的内容
     */
    private List<Map<String, Object>> listItems;

    /**
     * 定义构造方法
     */
    public InitUtil(Context context) {
        this.intData(context);
    }

    public void intData(Context context) {
        ListItemContentEntity itemContent = null;
        String name = "周杰伦";
        String describe = "如果你不能简洁的表达你的想法，说明你不够了解它";
        imageSlideIds = new int[]{R.drawable.slide_show_01, R.drawable.slide_show_02,
                R.drawable.slide_show_03, R.drawable.slide_show_04, R.drawable.slide_show_05};
        iconIds = new int[]{R.drawable.icon_01, R.drawable.icon_02, R.drawable.icon_03,
                R.drawable.icon_04, R.drawable.icon_05, R.drawable.icon_06, R.drawable.icon_07};
        listItemContent = new ArrayList<>();
        listItems = new ArrayList<Map<String, Object>>();
        //Log.d("StevenGo","循环上面的一切正常");

        //对列表项进行封装
        for (int i = 0; i < iconIds.length; i++) {
            itemContent = new ListItemContentEntity();
            itemContent.setName(name);
            itemContent.setDescribe(describe);

            itemContent.setImageIconIds(iconIds[i]);
            listItemContent.add(itemContent);
        }

        //准备列表里面显示的内容
        for (int i = 0; i < listItemContent.size(); i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("name", listItemContent.get(i).getName());
            listItem.put("describe", listItemContent.get(i).getDescribe());
            listItem.put("icon", listItemContent.get(i).getImageIconIds());
            listItems.add(listItem);
        }
    }

    public List<Map<String, Object>> getListItems() {
        return listItems;
    }

    public int[] getImageSlideIds() {
        return imageSlideIds;
    }
}

