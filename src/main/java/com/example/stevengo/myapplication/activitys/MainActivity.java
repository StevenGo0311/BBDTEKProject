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
 * */
public class MainActivity extends BaseActivity implements View.OnClickListener{
    /**音乐图标*/
    @BindView(R.id.id_imageview_icon_music)
    ImageView mIconMusic;
    /**资讯图标*/
    @BindView(R.id.id_imageview_icon_info)
    ImageView mIconInfo;
    /**我的图标*/
    @BindView(R.id.id_imageview_icon_my)
    ImageView mIconMy;
    /**音乐图标的容器*/
    @BindView(R.id.id_linearlayout_navigation_music)
    LinearLayout mLinearLayoutMusic;
    /**资讯图标的容器*/
    @BindView(R.id.id_linearlayout_navigation_info)
    LinearLayout mLinearLayoutInfo;
    /**我的图标的容器*/
    @BindView(R.id.id_linearlayout_navigation_my)
    LinearLayout mLinearLayoutMy;

    /**fragment管理器*/
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.activity_main);
        //绑定组件
        ButterKnife.bind(this);
        fragmentManager=getFragmentManager();
        //清除icon的设置效果
        clearIcon();
        //设置默认的fragment
        setDefaultFragment();
        //对navigationbar的三个按钮设置监听
        mLinearLayoutMusic.setOnClickListener(this);
        mLinearLayoutInfo.setOnClickListener(this);
        mLinearLayoutMy.setOnClickListener(this);
    }

    /**清除按钮的显示效果*/
    private void clearIcon(){
        mIconMusic.setSelected(false);
        mIconInfo.setSelected(false);
        mIconMy.setSelected(false);
    }
    /**设置默认的fragment*/
    private void setDefaultFragment(){
        //1.将music设置未选中状态
        mIconMusic.setSelected(true);
        //2.添加fragment
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.id_linearlayout_main,new MusicFragment());
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        //1.清除icon的显示效果
        //2.将当前点击的按钮设置为选中状态
        //3.修改fragment
        Log.d("StevenGo",String.valueOf(fragmentManager.getBackStackEntryCount()));
        switch (view.getId()){
            case R.id.id_linearlayout_navigation_music:
                clearIcon();
                mIconMusic.setSelected(true);
                changeFragment(new MusicFragment());
                break;
            case R.id.id_linearlayout_navigation_info:
                clearIcon();
                mIconInfo.setSelected(true);
                changeFragment(new InfoFragment());
                break;
            case R.id.id_linearlayout_navigation_my:
                clearIcon();
                mIconMy.setSelected(true);
                changeFragment(new MyFragment());
                break;
        }
    }
    /**修改framgnet*/
    private void changeFragment(Fragment fragment){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.id_linearlayout_main,fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
    /**重写返回键的监听，直接结束掉本activity*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return true;
    }
    @Override
    protected View getCustomerActionBar() {
        return null;
    }
}
