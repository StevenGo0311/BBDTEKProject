package com.example.stevengo.myapplication.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.stevengo.myapplication.R;
/**
 * @author StevenGo
 * 自定义PageAdapter,实现无限循环
 */
public class SlideShowAdapter extends PagerAdapter {
    /**接受调用者传递过来Context*/
    private Context mContext;
    /**在ViewPager显示的图片*/
    private int[] imageIds=new int[]{
            R.drawable.slide_show_01,
            R.drawable.slide_show_02,
            R.drawable.slide_show_03,
            R.drawable.slide_show_04,
            R.drawable.slide_show_05
    };
    public SlideShowAdapter(Context context){
        mContext=context;
    }

    /**返回条目的总数*/
    @Override
    public int getCount() {
        //将整型最大值作为Item总数
        return Integer.MAX_VALUE;
         //return imageList.size();
    }
    /**返回要显示的条目，并创建条目*/
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //Log.d("StevenGo","动一次打一次position："+position);
        //取模，确定要显示的图片
        int newPotion=position%imageIds.length;
        //通过反射机制加载布局
        View linearLayout=LayoutInflater.from(mContext).inflate(R.layout.view_pager_content,null);
        //获取布局文件中的ImageView
        ImageView imageView=(ImageView) linearLayout.findViewById(R.id.view_pager_imageview);
        //设置背景
        imageView.setBackgroundResource(imageIds[newPotion]);
        //将view添加到容器中
        container.addView(linearLayout);
        return linearLayout;
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
