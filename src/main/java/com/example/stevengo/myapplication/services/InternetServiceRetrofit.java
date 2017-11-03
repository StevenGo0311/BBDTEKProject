package com.example.stevengo.myapplication.services;

import com.example.stevengo.myapplication.base.ApiBase;
import com.example.stevengo.myapplication.base.UrlConsTable;
import com.example.stevengo.myapplication.entitys.MusicEntity;
import com.example.stevengo.myapplication.entitys.Parameter;
import com.example.stevengo.myapplication.utils.FastJsonConverterFactory;
import com.example.stevengo.myapplication.utils.InternetUtilRetrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by StevenGo on 2017/10/12.
 * 读取网络数据的服务
 */

public class InternetServiceRetrofit {
    /**
     * 创建LoadMusic类型的变量
     */
    private LoadMusic loadMusic;

    /**
     * 从互联网读取数据
     */
    public InternetServiceRetrofit() {

    }

    public InternetServiceRetrofit(LoadMusic loadMusic) {
        this.loadMusic = loadMusic;
    }

    public void doGet(Parameter parameter) {
        //记录从互联上网得到的所有音乐的信息
        Retrofit retrofit = new Retrofit.Builder()
                //添加baseUrl
                .baseUrl(UrlConsTable.URL_BASE)
                //添加转化器，将json映射到对象
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();

        //显示对话框，开始读取网络数据
        loadMusic.loadStart();
        ApiBase apiBase = retrofit.create(ApiBase.class);
        Call<MusicEntity> musicEntityCall = apiBase.getMusic(parameter.getKw(), parameter.getPi(), parameter.getPz());
        //异步处理
        musicEntityCall.enqueue(new Callback<MusicEntity>() {
            @Override
            public void onResponse(Call<MusicEntity> call, Response<MusicEntity> response) {
                //转换获得数据
                List<MusicEntity.TracksBean> tracksBeen = response.body().getTracks();
                //判断时否从网络上得到了数据
                if (tracksBeen == null) {
                    tracksBeen = new ArrayList<MusicEntity.TracksBean>();
                }
                //加载数据完成
                loadMusic.loadFinish(tracksBeen);
            }

            @Override
            public void onFailure(Call<MusicEntity> call, Throwable t) {

            }
        });
    }

    public void setLoadMusic(LoadMusic loadMusic) {
        this.loadMusic = loadMusic;
    }

    /**
     * 定义从网上获取数据的接口
     */
    public interface LoadMusic {
        void loadStart();

        void loadFinish(List<MusicEntity.TracksBean> tracksBeen);
    }

}
