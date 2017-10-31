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
    /**判断网络是否可用*/
    public static boolean isInternetConnected(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //网络标志初始化为false
        boolean flag=false;
        if(connectivityManager!=null){
            //获取所有连接
            NetworkInfo[] netWorkInfos=connectivityManager.getAllNetworkInfo();
            if (netWorkInfos != null) {
                //遍历连接
                for (int i = 0; i < netWorkInfos.length; i++) {
                    //当有连接的时候把标志置为true
                    if (netWorkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                        flag=true;
                    }
                }
            }
        }
        //判断有无网络连接
        if(!flag) {
            return false;
        }else{
            return true;
        }
    }
}
