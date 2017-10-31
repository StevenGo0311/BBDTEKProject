package com.example.stevengo.myapplication.activitys;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.services.LocationServicesAmap;
import com.example.stevengo.myapplication.utils.PermissionsChecker;

import junit.framework.Test;

/**
 * @author StevenGo
 * 闪屏，检测系统权限，初始化定位
 * */
public class StartActivity extends BaseActivity{

    /*需要进行检测的权限数组*/
    protected String[] needPermissions = {
            //网络
            Manifest.permission.INTERNET,
            //向内存卡写入数据
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            //读取网络状态
            Manifest.permission.ACCESS_NETWORK_STATE,
            //访问程序wifi网络信息
            Manifest.permission.ACCESS_WIFI_STATE,
            //获取手机状态和身份
            Manifest.permission.READ_PHONE_STATE,
            //方位粗略的位置
            Manifest.permission.ACCESS_COARSE_LOCATION,
            //获取精确位置
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    /**权限检测器*/
    private PermissionsChecker mPermissionsChecker;
    private LocationServicesAmap mLocationServicesAmap;
    /**请求码*/
    private static final int REQUEST_CODE = 0;
    /**该屏幕停留的时间*/
    private static final int DELAY_MILLIONS=2*1000;
    private static final int MESSAGE_WHAT=0x001;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //打开主页面
            Intent intent=new Intent(StartActivity.this, MainActivity.class);
            //启动页面
            startActivity(intent);
            //结束该activity
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏显示
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        //加载布局
        setContentView(R.layout.activity_start);
        //初始权限检测器和定位服务
        mPermissionsChecker=new PermissionsChecker(this);
        mLocationServicesAmap=new LocationServicesAmap(getApplicationContext());
    }

    @Override
    protected View getCustomerActionBar() {
        return null;
    }

    //加入权限判断和定位功能
    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(needPermissions)) {
            startPermissionsActivity();
        }else{
            mLocationServicesAmap.startLocation();
            handler.sendEmptyMessageDelayed(MESSAGE_WHAT,DELAY_MILLIONS);
        }
    }
    /**启动设置权限的activity*/
    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, needPermissions);
    }
    /**判断是否授权*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            showToast("缺少权限");
            finish();
        }
    }


}
