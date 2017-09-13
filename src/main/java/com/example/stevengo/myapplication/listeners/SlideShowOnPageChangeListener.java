package com.example.stevengo.myapplication.listeners;

import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

/**
 * @author StevenGo
 * 自定义PagerViewe的OnPageChangeListener，并重写方法
 */

public class SlideShowOnPageChangeListener implements ViewPager.OnPageChangeListener {
    /**接收调用者传过来的容器*/
    private LinearLayout pointsContainer;
    /**记录轮播图中图片的数量*/
    private int imageNumber;
    /**记录PagerView中上一个Item*/
    private int previousSelectedItem=0;
    /**ViewPager*/;
    private boolean isAutoRun;

    public SlideShowOnPageChangeListener(LinearLayout pointsCon,int imageNum){
        pointsContainer=pointsCon;
        imageNumber=imageNum;
    }
    /**重写onPageScrolled的方法，加入改变指示器功能*/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int newPosition = position % imageNumber;
        //将上一个设置成false,将本次显示的Item设置成true
        pointsContainer.getChildAt(previousSelectedItem).setEnabled(false);
        pointsContainer.getChildAt(newPosition).setEnabled(true);
        previousSelectedItem = newPosition;
    }
    @Override
    public void onPageSelected(int position) {
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
