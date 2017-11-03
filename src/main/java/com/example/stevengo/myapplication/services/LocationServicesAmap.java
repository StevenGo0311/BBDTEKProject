package com.example.stevengo.myapplication.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.stevengo.myapplication.BangMusicApplication;

/**
 * Created by StevenGo on 2017/10/26.
 * 用高德定位的服务
 */

public class LocationServicesAmap {
    /**
     * 定义定位器
     */
    private AMapLocationClient mLocationClient = null;
    /**
     * 定义定位参数
     */
    private AMapLocationClientOption mLocationClientOption = null;
    /**
     * 上下文
     */
    private Context mContext;
    private GetLocation getLocation;

    /**
     * 重写构造，加入各种初始化功能
     */
    public LocationServicesAmap(Context context) {
        mContext = context;
        /**初始化定位*/
        mLocationClient = new AMapLocationClient(mContext);
        /**初始化定位参数*/
        mLocationClientOption = new AMapLocationClientOption();
        /**设置为一次定位*/
        mLocationClientOption.setOnceLocation(true);
        /**设置定位参数*/
        mLocationClient.setLocationOption(mLocationClientOption);
        /**设置定位监听*/
        mLocationClient.setLocationListener(mLocationListener);
    }

    public LocationServicesAmap(Context context, GetLocation getLocation) {
        this(context);
        this.getLocation = getLocation;
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        mLocationClient.startLocation();
    }

    /**
     * 定位监听器
     */
    private AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //自定义的application
            BangMusicApplication application = (BangMusicApplication) mContext;
            //判断有无定位信息，如果有则将定位信息保存到application
            if (aMapLocation != null) {
                //错误码是0的时候表示定位正常
                Log.d("StevenGo", "错误码" + aMapLocation.getErrorCode());

                if (aMapLocation.getErrorCode() == 0) {
                    application.setLocationCity(aMapLocation.getCity());
                    application.setLocationDistrict(aMapLocation.getDistrict());
                    application.setLongitude(aMapLocation.getLongitude());
                    application.setLatitude(aMapLocation.getLatitude());
                    getLocation.locationfinish();
                } else {
                    Toast.makeText(mContext, "定位发生异常", Toast.LENGTH_SHORT).show();
                    application.setLocationCity("");
                    application.setLocationDistrict("定位发生异常");
                    getLocation.locationfinish();
                }
                //如果没有定位信息，打印提示
            } else {
                Toast.makeText(mContext, "未得到位置", Toast.LENGTH_SHORT).show();
                application.setLocationCity("");
                application.setLocationDistrict("未得到位置");
                getLocation.locationfinish();
            }
        }
    };

    public void setGetLocationInterface(GetLocation getLocation) {
        this.getLocation = getLocation;

    }

    public interface GetLocation {
        void locationfinish();
    }
}
