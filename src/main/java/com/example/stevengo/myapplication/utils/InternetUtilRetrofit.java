package com.example.stevengo.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.stevengo.myapplication.entitys.MusicEntity;
import com.example.stevengo.myapplication.entitys.Parameter;
import com.example.stevengo.myapplication.base.UrlConsTable;
import com.example.stevengo.myapplication.base.ApiBase;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by StevenGo on 2017/9/30.
 * 以retrofit方式访问网络资源的工具类
 */
public class InternetUtilRetrofit {
    public static List<MusicEntity.TracksBean> doGet(Parameter parameter) {
        //记录从互联上网得到的所有音乐的信息
        List<MusicEntity.TracksBean> tracksBeans=null;
        Retrofit retrofit=new Retrofit.Builder()
                //添加baseUrl
                .baseUrl(UrlConsTable.URL_BASE)
                //添加转化器，将json映射到对象
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiBase apiBase =retrofit.create(ApiBase.class);
        Call<MusicEntity> musicEntityCall= apiBase.getMusic(parameter.getKw(),parameter.getPi(),parameter.getPz());
        //异步处理
//        musicEntityCall.enqueue(new Callback<MusicEntity>() {
//            @Override
//            public void onResponse(Call<MusicEntity> call, Response<MusicEntity> response) {
//                tracksBeans=response.body().getTracks();
//                Log.d("StevenGo", "onResponse: "+"");
//                for (int i = 0; i <tracksBeans.size() ; i++) {
//                    Log.d("StevenGo", "onResponse: "+tracksBeans.get(i).getTitle());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MusicEntity> call, Throwable t) {
//
//            }
//        });
        //同步处理Call，将json转换成对象
        //同步处理Call，将json转换成对象
        try{
            tracksBeans=musicEntityCall.execute().body().getTracks();
        }catch (IOException e){

        }
        return tracksBeans;
    }
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
