package com.example.stevengo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;
import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    private ViewPager viewPager;
    private int[] imageResIds;
    private List<ImageView> imageViewList;
    private LinearLayout mPointsLayout;
    private int previousSelectedItem;
    private Handler mHandler;
    boolean isAutoRun = true;
    boolean isActivityAlive = true;
    private int delayMillis = 2000;

    private ListView listView;
    private String[] names;
    private String[] describes;
    private int[] imageIconIds;
    private List<Map<String,Object>> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化视图
        initViews();
        Log.d("StevenGo","初始化视图完成");
        //初始化数据
        initData();
        //初始化适配器
        Log.d("StevenGo","初始化数据完成");
        initAdapter();
        //开始自动播放
        startRun();
    }
    //开始自动播放
    private void startRun() {
        mHandler = new Handler();
        mHandler.postDelayed(mTaskRunnable, delayMillis);

    }

    //该线程一直运行着，知道activity被销毁，此时将isActivityAlive设置为false
    final Runnable mTaskRunnable = new Runnable() {
        @Override
        public void run() {
            // 如果activity未被销毁，就一直执行该线程
            // 在ViewPager的OnPageChangeListener方法中决定是否将isAutoRun置反
            if (isActivityAlive) {
                if (isAutoRun) {
                    viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % imageViewList.size());
                    mHandler.postDelayed(mTaskRunnable, delayMillis);
                } else {
                    mHandler.postDelayed(mTaskRunnable, delayMillis);
                }
            }
        }
    };

    private void initAdapter() {
        //int firstPosition = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        //viewPager.setOffscreenPageLimit(imageViewList.size());
        viewPager.setAdapter(new MyAdapter());
        // 设置从中间的某个位置开始滑动，从而能够实现向左向右的循环滑动
        //viewPager.setCurrentItem(firstPosition);
        viewPager.setCurrentItem(0);


        SimpleAdapter simpleAdapter=new SimpleAdapter(this,listItems,R.layout.list_item,new String[]{"name","describe","icon"},new int[]{R.id.listview_item_name,R.id.listview_item_describe,R.id.list_item_icon});
        listView.setAdapter(simpleAdapter);
//        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,names);
//        listView.setAdapter(arrayAdapter);
    }

    private void initData() {

        //图片资源id数组
        imageResIds = new int[]{R.drawable.slide_show_01, R.drawable.slide_show_02, R.drawable.slide_show_03, R.drawable.slide_show_04, R.drawable.slide_show_05};
        imageViewList = new ArrayList<>();
        listItems=new ArrayList<Map<String,Object>>();
        names=new String[]{
                "周杰伦",
                "周杰伦",
                "周杰伦",
                "周杰伦",
                "周杰伦",
                "周杰伦",
                "周杰伦"
        };
        describes=new String[]{
                "如果你不能简洁的表达你的想法，说明你不够了解它",
                "如果你不能简洁的表达你的想法，说明你不够了解它",
                "如果你不能简洁的表达你的想法，说明你不够了解它",
                "如果你不能简洁的表达你的想法，说明你不够了解它",
                "如果你不能简洁的表达你的想法，说明你不够了解它",
                "如果你不能简洁的表达你的想法，说明你不够了解它",
                "如果你不能简洁的表达你的想法，说明你不够了解它"
        };
        imageIconIds=new int[]{
                R.drawable.icon_01,
                R.drawable.icon_02,
                R.drawable.icon_03,
                R.drawable.icon_04,
                R.drawable.icon_05,
                R.drawable.icon_06,
                R.drawable.icon_07,

        };
        Log.d("StevenGo","1没问题");
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置圆点之间的距离
        mParams.leftMargin = 15;
        mParams.topMargin = 2;

        ImageView imageView;
        ImageView pointView;
        //初始化要展示的ImageView,并添加圆点指示器
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化图片
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            // 初始化指示器
            pointView = new ImageView(this);
            pointView.setBackgroundResource(R.drawable.point);
            pointView.setLayoutParams(mParams);
            if (i == 0) {
                pointView.setEnabled(true);
            } else {
                pointView.setEnabled(false);
            }
            mPointsLayout.addView(pointView);
        }
        Log.d("StevenGo","2没问题");
        for(int i=0;i<names.length;i++){
            Map<String,Object> listItem=new HashMap<String,Object>();
            listItem.put("name",names[i]);
            Log.d("StevenGo","name"+names[i]);
            listItem.put("describe",describes[i]);
            listItem.put("icon",imageIconIds[i]);
            listItems.add(listItem);
        }
        Log.d("StevenGo","3没问题");
        Log.d("StevenGo","list的长度"+listItems.size());


    }

    private void initViews() {

        //获取viewpager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //添加页面改变监听器
        viewPager.addOnPageChangeListener(this);
        // 用来添加圆点指示器
        mPointsLayout = (LinearLayout) findViewById(R.id.point_container);
        listView=(ListView)findViewById(R.id.list);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int newPosition = position % imageViewList.size();

        // 先将上一个置位false，将当前位置置位true，这样可以使得初始化的时候就在第一个位置
        // （因为previousSelectedItem的未赋值时候的初始值默认为0）
        mPointsLayout.getChildAt(previousSelectedItem).setEnabled(false);
        mPointsLayout.getChildAt(newPosition).setEnabled(true);
        previousSelectedItem = newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            // 静止状态
            case SCROLL_STATE_IDLE:
                isAutoRun = true;
                break;
            // 拖拽中
            case SCROLL_STATE_DRAGGING:
                isAutoRun = false;
                break;
            // 拖拽后松手，自动回到最终位置的过程
            case SCROLL_STATE_SETTLING:
                isAutoRun = true;
                break;
        }
    }

    // 创建一个MyAdapter类，继承自PagerAdapter来为ViewPager设置适配器
    class MyAdapter extends PagerAdapter {

        // 1.返回条目的总数
        @Override
        public int getCount() {

            return imageViewList.size();
//            return Integer.MAX_VALUE;
        }

        // 2.返回要显示的条目，并创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //container:容器，其实也就是ViewPager
            //position:当前要显示的条目的位置

            int newPosition = position % imageViewList.size();
            ImageView imageView = imageViewList.get(newPosition);
            //a.将View对象添加到container容器中
            container.addView(imageView);
            //b.把View对象返回给框架，适配器
            return imageView;
        }

        // 3.销毁条目，其实就是将要销毁的对象object从container中移除出去就好了
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        // 4.指定复用的判断逻辑（一般为固定写法）
        @Override
        public boolean isViewFromObject(View view, Object object) {
            // 当滑动到新的条目之后，又返回回来，view是否可以被复用
            return view == object;
        }
    }

    @Override
    protected void onDestroy() {
        isActivityAlive = false;
        super.onDestroy();
    }
}

