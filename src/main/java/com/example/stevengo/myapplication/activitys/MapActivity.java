package com.example.stevengo.myapplication.activitys;

import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.stevengo.myapplication.BangMusicApplication;
import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.utils.GeneralUtil;
import com.example.stevengo.myapplication.utils.LocationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 显示地图的activity
 */
public class MapActivity extends BaseActivity {
    /**地图视图*/
    private MapView mapView;
    /**经度*/
    private double locationLongitude;
    /**纬度*/
    private double locationLatitude;
    private ImageView mImageViewButtonBack;
    private TextView mTextViewTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //从布局文件中获取mapView
        mapView=(MapView)findViewById(R.id.mapview_map);
        mapView.onCreate(savedInstanceState);
        locationLongitude=((BangMusicApplication)getApplication()).getLongitude();
        locationLatitude=((BangMusicApplication)getApplication()).getLatitude();
        //的到AMap对象
        AMap aMap=mapView.getMap();
//        Log.d("StevenGo",String.valueOf(locationLongitude));
//        Log.d("StevenGo",String.valueOf(locationLatitude));
        if(((BangMusicApplication)getApplication()).getLatitude()<91&&((BangMusicApplication)getApplication()).getLongitude()<181){
            //设置地图的中心为定位的中心
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(locationLatitude,locationLongitude)));
            //设置地图缩放比
            aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
            //在地图上添加覆盖物，位置为定位的位置
            Marker marker = aMap.addMarker(new MarkerOptions()
                    //设置覆盖物位置
                    .position(new LatLng(locationLatitude,locationLongitude))
                    //设置图标
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(),R.drawable.icon_location_small)))
                    //设置拖动性
                    .draggable(true));
        }else{
            showToast("定位失败");
        }

    }

    @Override
    protected View getCustomerActionBar() {
        //获取actionbar的布局
        View view=LayoutInflater.from(this).inflate(R.layout.actionbar_general,null);
        //从布局中得到按钮和title
        mImageViewButtonBack=(ImageView) view.findViewById(R.id.imageview_actionbar_general_back);
        mTextViewTitle=(TextView)view.findViewById(R.id.textview_actionbar_general_title);
        mTextViewTitle.setText(R.string.map);
        mImageViewButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        return view;
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
    @OnClick(R.id.id_imageview_back_map)
    public void backMap(View view){
        finish();
    }
}

