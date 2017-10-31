package com.example.stevengo.myapplication.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.stevengo.myapplication.BangMusicApplication;
import com.example.stevengo.myapplication.R;

/**
 * Created by StevenGo on 2017/10/26.
 * 用高德定位的服务
 */

public class LocationServicesAmap {
    /**定义定位器*/
    private AMapLocationClient mLocationClient=null;
    /**定义定位参数*/
    private AMapLocationClientOption mLocationClientOption=null;
    /**上下文*/
    private Context mContext;
    /**重写构造，加入各种初始化功能*/
    public LocationServicesAmap(Context context){
        mContext=context;
        /**初始化定位*/
        mLocationClient=new AMapLocationClient(mContext);
        /**初始化定位参数*/
        mLocationClientOption=new AMapLocationClientOption();
        /**设置为一次定位*/
        mLocationClientOption.setOnceLocation(true);
        /**设置定位参数*/
        mLocationClient.setLocationOption(mLocationClientOption);
        /**设置定位监听*/
        mLocationClient.setLocationListener(mLocationListener);
    }
    /**开始定位*/
    public void startLocation(){
        mLocationClient.startLocation();
    }
    /**定位监听器*/
    private AMapLocationListener mLocationListener=new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //自定义的application
            BangMusicApplication application=(BangMusicApplication)mContext;
            //判断有无定位信息，如果有则将定位信息保存到application
            if(aMapLocation!=null){
                if(aMapLocation.getErrorCode()==0){
                    application.setLocationCity(aMapLocation.getCity());
                    application.setLocationDistrict(aMapLocation.getDistrict());
                    application.setLongitude(aMapLocation.getLongitude());
                    application.setLatitude(aMapLocation.getLatitude());
                    Log.d("StevenGo",application.getLocationCity());
                    Log.d("StevenGo",application.getLocationDistrict());
                }else{
                    Toast.makeText(mContext, "定位发生异常", Toast.LENGTH_SHORT).show();
                    application.setLocationCity("");
                    application.setLocationDistrict("定位发生异常");
                }
            //如果没有定位信息，打印提示
            }else{
                Toast.makeText(mContext, "未得到位置", Toast.LENGTH_SHORT).show();
                application.setLocationCity("");
                application.setLocationDistrict("未得到位置");
            }
        }
    };
}
