package com.example.stevengo.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.stevengo.myapplication.entitys.MusicEntity;
import com.example.stevengo.myapplication.entitys.Parameter;
import com.example.stevengo.myapplication.base.UrlConsTable;
import com.example.stevengo.myapplication.base.ApiBase;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by StevenGo on 2017/9/30.
 * 以retrofit方式访问网络资源的工具类
 */
public class InternetUtilRetrofit {
    private static List<MusicEntity.TracksBean> tracksBeans = null;

    public static List<MusicEntity.TracksBean> doGet(Parameter parameter) {
        //记录从互联上网得到的所有音乐的信息
        Retrofit retrofit = new Retrofit.Builder()
                //添加baseUrl
                .baseUrl(UrlConsTable.URL_BASE)
                //添加转化器，将json映射到对象
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
        ApiBase apiBase = retrofit.create(ApiBase.class);

        Call<MusicEntity> musicEntityCall = apiBase.getMusic(parameter.getKw(), parameter.getPi(), parameter.getPz());
        try {
            tracksBeans = musicEntityCall.execute().body().getTracks();
        } catch (IOException e) {

        }
        return tracksBeans;
    }
}
