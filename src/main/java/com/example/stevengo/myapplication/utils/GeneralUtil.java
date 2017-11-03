package com.example.stevengo.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by StevenGo on 2017/10/12.
 */

public class GeneralUtil {
    /**
     * 判断网络是否可用
     */
    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //网络标志初始化为false
        boolean flag = false;
        if (connectivityManager != null) {
            //获取所有连接
            NetworkInfo[] netWorkInfos = connectivityManager.getAllNetworkInfo();
            if (netWorkInfos != null) {
                //遍历连接
                for (int i = 0; i < netWorkInfos.length; i++) {
                    //当有连接的时候把标志置为true
                    if (netWorkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                        flag = true;
                    }
                }
            }
        }
        //判断有无网络连接
        if (!flag) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 设置沉浸式状态栏
     */
    public static void translucentStatus(Activity activity) {
        //设置全屏
        activity.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //判断系统版本是否大于LOLLIPOP
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
