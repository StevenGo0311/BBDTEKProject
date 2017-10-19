package com.example.stevengo.myapplication.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.stevengo.myapplication.base.ApiBase;
import com.example.stevengo.myapplication.base.UrlConsTable;
import com.example.stevengo.myapplication.entitys.LocationInfo;
import com.example.stevengo.myapplication.entitys.MusicEntity;
import com.example.stevengo.myapplication.entitys.Parameter;
import com.example.stevengo.myapplication.entitys.ParameterLocation;
import com.example.stevengo.myapplication.utils.FastJsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by StevenGo on 2017/10/17.
 */

public class InternetServiceLocation {
    private GetLocation getLocation;
    public InternetServiceLocation(GetLocation getLocation){
        this.getLocation=getLocation;
    }
    public  void doGet(final Context context, ParameterLocation parameterLocation) {
        //记录从互联上网得到的所有音乐的信息
        Retrofit retrofit=new Retrofit.Builder()
                //添加baseUrl
                .baseUrl(UrlConsTable.LOCATION_URL_BASE)
                //添加转化器，将json映射到对象
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();
        //显示对话框，开始读取网络数据
        ApiBase apiBase =retrofit.create(ApiBase.class);
        Call<LocationInfo> locationInfosCall= apiBase.getLocation(parameterLocation.getKey(),parameterLocation.getLocation());
        //异步处理
        locationInfosCall.enqueue(new Callback<LocationInfo>() {
            @Override
            public void onResponse(Call<LocationInfo> call, Response<LocationInfo> response) {
                String province="";
                String district="";
                LocationInfo locationInfo=response.body();
                if(locationInfo!=null){
                    province=locationInfo.getRegeocode().getAddressComponent().getProvince();
                    district=locationInfo.getRegeocode().getAddressComponent().getDistrict();
                    getLocation.getLocation(province,district);
                }else{
                    Toast.makeText(context,"获取位置失败",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LocationInfo> call, Throwable t) {

            }
        });
    }
    public interface GetLocation{
        void getLocation(String province,String district);
    }
}
