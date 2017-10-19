package com.example.stevengo.myapplication.activitys;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.stevengo.myapplication.*;
import com.example.stevengo.myapplication.base.UrlConsTable;
import com.example.stevengo.myapplication.entitys.ParameterLocation;
import com.example.stevengo.myapplication.services.InternetServiceLocation;
import com.example.stevengo.myapplication.services.InternetServiceLocation.GetLocation;

import com.example.stevengo.myapplication.utils.LocationUtil;
import com.example.stevengo.myapplication.utils.PermissionsChecker;

/**
 * @author StevenGo
 * 闪频，检测系统权限，初始化定位
 * */
public class StartActivity extends AppCompatActivity implements GetLocation {
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int REQUEST_CODE = 0;
    private PermissionsChecker mPermissionsChecker;
    private InternetServiceLocation internetServiceLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //加载布局
        setContentView(R.layout.activity_start);
        mPermissionsChecker=new PermissionsChecker(this);
        internetServiceLocation=new InternetServiceLocation(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(needPermissions)) {
            startPermissionsActivity();
        }else{
            Location location= LocationUtil.getLocation(getApplicationContext());
            if(location!=null){
                String locationString=location.getLongitude()+","+location.getLatitude();
                internetServiceLocation.doGet(this,new ParameterLocation(UrlConsTable.LOCATION_KEY,locationString));
                Log.d("StevenGo",locationString);
            }
        }
    }


    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, needPermissions);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }
    @Override
    public void getLocation(String province, String district) {
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("province",province);
        intent.putExtra("district",district);
        startActivity(intent);
        finish();
    }
}
