package com.example.stevengo.myapplication.fragments;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.activitys.MapActivity;
import com.example.stevengo.myapplication.adapters.SlideShowAdapter;
import com.example.stevengo.myapplication.listeners.SlideShowOnPageChangeListener;
import com.example.stevengo.myapplication.utils.InitUtil;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class MusicFragment extends Fragment {
    /**轮播图*/
    @BindView(R.id.viewpager) ViewPager mViewPager;
    /**显示歌手信息的listView*/
    @BindView(R.id.list) ListView mListView;
    /**轮播指示器*/
    @BindView(R.id.point_container) LinearLayout mPointContainer;
    /**省份*/
    @BindView(R.id.textview_location_province)TextView mTextViewProvince;
    /**区域*/
    @BindView(R.id.textview_location_city)TextView mTextViewCity;
    /**省份和区域的容器*/
    @BindView(R.id.linearlayout_location)LinearLayout mLinearLayout;
    @BindView(R.id.button_search_activity_main) Button mButtonSearch;

    /**管理轮播指示器圆点的容器*/
    private LinearLayout.LayoutParams mlayoutParams;
    /**轮播指示器中的圆点*/
    private ImageView point;

    /**用于初始化的工具*/
    private InitUtil initUtil;
    /**新线程*/
    private Runnable runnable;
    /**标识Activity是否可用*/
    private boolean isActivityAlive=true;
    /**轮播图的等待时间*/
    private final int DELAY_TIME=5*1000;
    /**消息的标识*/
    private final int MESSAGE_WHAT=0X01;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    /**控制自动播放*/
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //将显示的Item移动到下一个位置
            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            if(isActivityAlive){
                mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT,DELAY_TIME);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUtil=new InitUtil(getActivity());
        fragmentManager=getFragmentManager();
    }
    /**轮播图的监听器*/
    private SlideShowOnPageChangeListener slideShowOnPageChangeListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_music,null);
        ButterKnife.bind(this,view);
        //初始化视图
        initViews();
        //添加适配器
        addAdapter();
        //给PagerView添加监听器
        addSlideShowOnPageChangeListener();
        mHandler.sendEmptyMessageDelayed(MESSAGE_WHAT,DELAY_TIME);
        return view;
    }
    /**初始化视图*/
    private void initViews(){
        mTextViewProvince.setText(getActivity().getIntent().getStringExtra("province"));
        mTextViewCity.setText(getActivity().getIntent().getStringExtra("district"));
        mlayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置圆点之间的距离
        mlayoutParams.leftMargin=15;
        mlayoutParams.topMargin=2;
        //创建指示器中的小圆点，并将其添加到容器中
        for(int i=0;i<initUtil.getImageSlideIds().length;i++){
            point=new ImageView(getActivity());
            point.setBackgroundResource(R.drawable.point);
            point.setLayoutParams(mlayoutParams);
            //设置小圆点的状态
            point.setEnabled(false);
            mPointContainer.addView(point);
        }
    }
    /**初始化Adapter,填充内容*/
    private void addAdapter(){
        mViewPager.setAdapter(new SlideShowAdapter(getActivity()));
        //轮播图的起始位置
        int currentItemId=Integer.MAX_VALUE/2;
        //修正起始位置，使图片从第一个开始
        while(currentItemId%initUtil.getImageSlideIds().length!=0){
            currentItemId++;
        }
        //设置起始图片
        mViewPager.setCurrentItem(currentItemId);
        //mViewPager.setCurrentItem(0);
        //列表设置Adapter
        mListView.setAdapter(new SimpleAdapter(getActivity(),initUtil.getListItems(),R.layout.list_item,
                new String[]{"name","describe","icon"},new int[]{R.id.listview_item_name,
                R.id.listview_item_describe,
                R.id.list_item_icon}));
    }
    /**封装添加监听器的过程*/
    private void addSlideShowOnPageChangeListener(){
        slideShowOnPageChangeListener=new SlideShowOnPageChangeListener(mPointContainer,initUtil.getImageSlideIds().length);
        mViewPager.addOnPageChangeListener(slideShowOnPageChangeListener);
    }
    /**重写onDestroy,Activity销毁时结束自动播放*/
    @Override
    public void onDestroy(){
        super.onDestroy();
        isActivityAlive=false;
    }
    /**linearlayout的单击操作，启动显示地图的activity*/
    @OnClick(R.id.linearlayout_location)
    public void startMapActivity(View view){
        Intent intent=new Intent(getActivity(),MapActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.button_search_activity_main)
    public void replaceFragment(View view){
        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.id_linearlayout_main,new SearchFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
