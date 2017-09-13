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
import java.util.Timer;
import java.util.TimerTask;
/**
 * @author StevenGo
 * 闪频，打开应用程序时显示这个界面，1.5s后跳转到主界面
 * */
public class StartActivity extends AppCompatActivity {
    /**创建定时器*/
    private Timer mTimer=null;
    /**创建消息处理器*/
    private Handler mHandle=null;

    /**设置发送消息开始时间和等待时间*/
    final int STARTMILLIS=1500;
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
        mTimer=new Timer();
        setHandle();
        setTimerTask();
        //finish();
    }
    private void setTimerTask(){
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //创建一条消息，并设置它的id
                Message message=new Message();
                message.what=0x101;
                mHandle.sendMessage(message);

            }
        },STARTMILLIS,DELAYMILLIS);
    }
    /**重写消息处理器，当定时器发来消息时，经过验证Id执行操作，这里启动MainActivity*/
    private void setHandle(){
        mHandle=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //获取消息的Id
                int msgId = msg.what;
                switch (msgId) {
                    case 0x101:
                        //启动MainActivity
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                        Log.d("StevenGo",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        break;
                    default:
                        break;
                }
            }
        };
    }
    /**当StartActivity被覆盖后，自动销毁*/
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
    /**重写onDestroy，当activity销毁时结束定时器的工作*/
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mTimer.cancel();
    }

}
