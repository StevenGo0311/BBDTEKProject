package com.example.stevengo.myapplication.activitys;

import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.utils.LocationUtil;

/**
 * 显示地图的activity
 */
public class MapActivity extends AppCompatActivity {
    /**地图视图*/
    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //定位
        Location location= LocationUtil.getLocation(getApplicationContext());
        //从布局文件中获取mapView
        mapView=(MapView)findViewById(R.id.mapview_map);
        mapView.onCreate(savedInstanceState);
        //的到AMap对象
        AMap aMap=mapView.getMap();
        if(location!=null){
            //设置地图的中心为定位的中心
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
            //设置地图缩放比
            aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
            //在地图上添加覆盖物，位置为定位的位置
            Marker marker = aMap.addMarker(new MarkerOptions()
                    //设置覆盖物位置
                    .position(new LatLng(location.getLatitude(),location.getLongitude()))
                    //设置图标
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),R.drawable.icon_location_small)))
                    //设置拖动性
                    .draggable(true));
        }else{
            Toast.makeText(this, "定位失败", Toast.LENGTH_SHORT).show();
        }

    }
    //重新方法，加入对于地图生命周期的控制
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}

