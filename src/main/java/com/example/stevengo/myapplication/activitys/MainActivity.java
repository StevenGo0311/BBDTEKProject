package com.example.stevengo.myapplication.activitys;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.fragments.InfoFragment;
import com.example.stevengo.myapplication.fragments.MusicFragment;
import com.example.stevengo.myapplication.fragments.MyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主页面，显示音乐fragment和导航栏
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 音乐图标
     */
    @BindView(R.id.id_imageview_icon_music)
    ImageView mIconMusic;
    /**
     * 资讯图标
     */
    @BindView(R.id.id_imageview_icon_info)
    ImageView mIconInfo;
    /**
     * 我的图标
     */
    @BindView(R.id.id_imageview_icon_my)
    ImageView mIconMy;
    /**
     * 音乐图标的容器
     */
    @BindView(R.id.id_linearlayout_navigation_music)
    LinearLayout mLinearLayoutMusic;
    /**
     * 资讯图标的容器
     */
    @BindView(R.id.id_linearlayout_navigation_info)
    LinearLayout mLinearLayoutInfo;
    /**
     * 我的图标的容器
     */
    @BindView(R.id.id_linearlayout_navigation_my)
    LinearLayout mLinearLayoutMy;

    /**
     * fragment管理器
     */
    FragmentManager fragmentManager;
    /**
     * 音乐fragment
     */
    MusicFragment musicFragment;
    /**
     * 资讯fragment
     */
    InfoFragment infoFragment;
    /**
     * 我的fragment
     */
    MyFragment myFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.activity_main);
        //绑定组件
        ButterKnife.bind(this);
        //清除icon的设置效果
        init();
        clearIcon();
        //设置默认的fragment
        setDefaultFragment();

        //对navigationbar的三个按钮设置监听
        mLinearLayoutMusic.setOnClickListener(this);
        mLinearLayoutInfo.setOnClickListener(this);
        mLinearLayoutMy.setOnClickListener(this);
    }

    /**
     * 初始化
     */
    private void init() {
        //得到fragment管理器
        fragmentManager = getFragmentManager();
        //创建可能用到的三个fragment
        musicFragment = new MusicFragment();
        infoFragment = new InfoFragment();
        myFragment = new MyFragment();
        //得到fragmentMangeer对象
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //添加fragment
        transaction.add(R.id.id_framelayout_main, musicFragment).add(R.id.id_framelayout_main, infoFragment).add(R.id.id_framelayout_main, myFragment);
        //隐藏frgment
        transaction.hide(musicFragment).hide(infoFragment).hide(myFragment);
        transaction.commit();
        //清除icon的选中状态
        clearIcon();
    }

    /**
     * 设置默认的fragment
     */
    private void setDefaultFragment() {
        //1.将music设置为选中状态
        mIconMusic.setSelected(true);
        //2.显示音乐的fragment
        fragmentManager.beginTransaction().show(musicFragment).commit();
    }

    /**
     * 清除按钮的显示效果
     */
    private void clearIcon() {
        mIconMusic.setSelected(false);
        mIconInfo.setSelected(false);
        mIconMy.setSelected(false);
    }

    /**
     * 隐藏所有的fragment
     */
    private void hideAllFragment() {
        fragmentManager.beginTransaction().hide(musicFragment).hide(infoFragment).hide(myFragment).commit();
    }

    @Override
    public void onClick(View view) {
        //1.清除icon的显示效果
        //2.隐藏所有的fragment
        //3.将当前点击的按钮设置为选中状态
        //4.显示被选中的fragment
        switch (view.getId()) {
            case R.id.id_linearlayout_navigation_music:
                clearIcon();
                hideAllFragment();
                mIconMusic.setSelected(true);
                fragmentManager.beginTransaction().show(musicFragment).commit();
                break;
            case R.id.id_linearlayout_navigation_info:
                clearIcon();
                hideAllFragment();
                mIconInfo.setSelected(true);
                fragmentManager.beginTransaction().show(infoFragment).commit();
                break;
            case R.id.id_linearlayout_navigation_my:
                clearIcon();
                hideAllFragment();
                mIconMy.setSelected(true);
                fragmentManager.beginTransaction().show(myFragment).commit();
                break;
        }
    }

    @Override
    protected View getCustomerActionBar() {
        return null;
    }
}
