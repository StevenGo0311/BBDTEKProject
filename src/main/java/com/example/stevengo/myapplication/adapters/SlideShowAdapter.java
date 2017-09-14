package com.example.stevengo.myapplication.adapters;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * @author StevenGo
 * 自定义PageAdapter,实现无限循环
 */

public class SlideShowAdapter extends PagerAdapter {
    /**用于接收调用者传过来的链表*/
    private List<ImageView> imageList;
    /**构造方法给成员变量赋值*/
    public SlideShowAdapter(List<ImageView> list ){
        imageList=list;

    }
    /**1.返回条目的总数*/
    @Override
    public int getCount() {
        //将整型最大值作为Item总数
        return Integer.MAX_VALUE;
         //return imageList.size();
    }
    /**返回要显示的条目，并创建条目*/
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //Log.d("StevenGo","position的值为："+position);
        //取模，达到无限循环的效果
        int newPosition = position % imageList.size();
        //从链表里获取单个图片
        ImageView imageView = imageList.get(newPosition);
        //将View对象添加到container容器中
        container.addView(imageView);
        //把View对象返回给框架，适配器
        return imageView;
    }

    /**销毁条目，其实就是将要销毁的对象object从container中移除出去就好了*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        //container.removeAllViews();
    }
    /**指定复用的判断逻辑*/
    @Override
    public boolean isViewFromObject(View view, Object object) {
        // 当滑动到新的条目之后，又返回回来，view是否可以被复用
        return view == object;
    }
}
