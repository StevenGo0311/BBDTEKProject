package com.example.stevengo.myapplication.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.adapters.SlideShowAdapter;
import com.example.stevengo.myapplication.listeners.SlideShowOnPageChangeListener;
import com.example.stevengo.myapplication.utils.InitUtil;

/**
 *@author StevenGo
 * 主界面，加载自定义的actionbar,轮播图和列表，并对这些组件进行初始化
 */
public class MainActivity extends AppCompatActivity {
    /**轮播图*/
    private ViewPager mViewPager;
    /**列表*/
    private ListView mListView;
    /**管理轮播指示器圆点的容器*/
    private LinearLayout.LayoutParams mlayoutParams;
    /**轮播指示器中的圆点*/
    private ImageView point;
    /**轮播指示器*/
    private LinearLayout mPointContainer;
    /**用于初始化的工具*/
    private InitUtil initUtil;
    /**控制自动播放*/
    private Handler mHandler;
    /**新线程*/
    private Runnable runnable;
    /**轮播图的等待时间*/
    private int delaytime=5000;
    /**标识Activity是否可用*/
    private boolean isActivityAlive=true;

    /**轮播图的监听器*/
    private SlideShowOnPageChangeListener slideShowOnPageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化工具
        initUtils();
        //初始化试图
        initViews();
        //用工具初始化数据
        initUtil.intData(this);
        //初始化适配器
        initAdapter();
        //给PagerView添加监听器
        addSlideShowOnPageChangeListener();
        //mViewPager.addOnPageChangeListener(new SlideShowOnPageChangeListener(mPointContainer,initUtil.getImageSlide().size()));

        //创建新线程
        newRunable();
        //开始自动播放
        startRun();
    }
    /**初始化视图*/
    private void initViews(){
        //根据Id获取界面组件
        mViewPager=(ViewPager)findViewById(R.id.viewpager);
        mListView=(ListView)findViewById(R.id.list);
        mPointContainer=(LinearLayout)findViewById(R.id.point_container);

        mlayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置圆点之间的距离
        mlayoutParams.leftMargin=15;
        mlayoutParams.topMargin=2;
        mPointContainer=(LinearLayout)findViewById(R.id.point_container);
        //创建指示器中的小圆点，并将其添加到容器中
        for(int i=0;i<initUtil.getImageSlide().size();i++){
            point=new ImageView(this);
            point.setBackgroundResource(R.drawable.point);
            point.setLayoutParams(mlayoutParams);
            //设置小圆点的状态
            point.setEnabled(false);
            mPointContainer.addView(point);
        }
    }
    /**初始化Adapter,填充内容*/
    private void initAdapter(){
        mViewPager.setAdapter(new SlideShowAdapter(initUtil.getImageSlide()));
        //轮播图的起始位置
        int currentItemId=Integer.MAX_VALUE/2;
        //修正起始位置，使图片从第一个开始
        while(currentItemId%initUtil.getImageSlide().size()!=0){
            currentItemId++;
        }
        //设置起始图片
        mViewPager.setCurrentItem(currentItemId);
        //mViewPager.setCurrentItem(0);
        //列表设置Adapter
        mListView.setAdapter(new SimpleAdapter(this,initUtil.getListItems(),R.layout.list_item,
                new String[]{"name","describe","icon"},new int[]{R.id.listview_item_name,
                R.id.listview_item_describe,
                R.id.list_item_icon}));

    }
    /**初始化工具类*/
    private void initUtils(){
        //初始化用于初始化的工具
        initUtil=new InitUtil(this);
    }
    /**开始自动播放*/
    private void startRun(){
        mHandler=new Handler();
        mHandler.postDelayed(runnable,delaytime);
    }
    /**创建新线程，用于图片轮播*/
    private void newRunable(){
        runnable=new Runnable() {
            @Override
            public void run() {
                if(isActivityAlive){
                    //打印当前id
                    //Log.d("StevenGo","当前位置的id："+mViewPager.getCurrentItem());
                    //将显示的Item移动到下一个位置
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                    mHandler.postDelayed(runnable,delaytime);

                }

            }
        };
    }
    /**封装添加监听器的过程*/
    private void addSlideShowOnPageChangeListener(){
        slideShowOnPageChangeListener=new SlideShowOnPageChangeListener(mPointContainer,initUtil.getImageSlide().size());
        mViewPager.addOnPageChangeListener(slideShowOnPageChangeListener);
    }
    /**重写onDestroy,Activity销毁时结束自动播放*/
    @Override
    protected void onDestroy(){
        super.onDestroy();
        isActivityAlive=false;
    }
    //事件处理绑定到标签
    public void openSearchActivity(View Source){
        //Log.d("StevenGo","调用这个函数");
        Intent intent=new Intent(MainActivity.this,SearchActivity.class);
        startActivity(intent);
    }

}
