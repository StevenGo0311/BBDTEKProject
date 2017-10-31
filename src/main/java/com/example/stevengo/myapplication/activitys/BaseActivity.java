package com.example.stevengo.myapplication.activitys;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.stevengo.myapplication.R;
/**
 *activity的超类，设置全屏，提供包括actionBar，Toast等公共部分 */

public abstract class BaseActivity extends AppCompatActivity{
    /**布局中actionbar的位置*/
    private FrameLayout mFrameLayoutContainerActionBar;
    /**布局中主体内容的位置*/
    private FrameLayout mFrameLayoutContainerContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        setFullSceeen();
        //初始化布局
        initContentView(R.layout.activity_base);
        //设置自定义actionbar
        getCustomerActionBar();
    }
    private void initContentView(@LayoutRes int layoutResID) {
        //得到窗口的根布局
        ViewGroup group = (ViewGroup) findViewById(android.R.id.content);
        //首先先移除在根布局上的组件
        group.removeAllViews();
        //group，true的意思表示添加上去
        LayoutInflater.from(this).inflate(layoutResID, group, true);
    }
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //获取组件
        mFrameLayoutContainerActionBar=(FrameLayout)findViewById(R.id.framelayout_container_actionbar);
        mFrameLayoutContainerContent=(FrameLayout)findViewById(R.id.framelayout_container_content);
        //将子类的内容布局添加到基类的内容布局中
        LayoutInflater.from(this).inflate(layoutResID, mFrameLayoutContainerContent, true);
        //判断actionbar是否为空，如果是空就隐藏掉，如果空进行设置
        if(getCustomerActionBar()!=null){
            mFrameLayoutContainerActionBar.addView(getCustomerActionBar());
            mFrameLayoutContainerActionBar.setVisibility(View.VISIBLE);
        }else{
            mFrameLayoutContainerActionBar.setVisibility(View.GONE);
        }
    }
    protected abstract View getCustomerActionBar();
    /**设置沉浸式状态栏*/
    private void setFullSceeen(){
        //设置全屏
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //判断系统版本是否大于LOLLIPOP
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    /**显示提示*/
    protected void showToast(String toastContent){
        Toast.makeText(this,toastContent,Toast.LENGTH_SHORT).show();
    }


}
