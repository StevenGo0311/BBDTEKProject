package com.example.stevengo.myapplication.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.stevengo.myapplication.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author StevenGo
 * 闪频，打开应用程序时显示该界面，1.5s后跳转到主界面
 * */
public class StartActivity extends AppCompatActivity {
    /**创建消息处理器*/
    private Handler mHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //启动MainActivity
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(intent);
            //Log.d("StevenGo", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));


        }
    };

    /**设置发送消息等待时间*/
    final int DELAYMILLIS=1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //加载布局
        setContentView(R.layout.activity_start);
        mHandle.sendMessageDelayed(new Message(),DELAYMILLIS);
    }

    /**当StartActivity被覆盖后，自动销毁*/
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

}
