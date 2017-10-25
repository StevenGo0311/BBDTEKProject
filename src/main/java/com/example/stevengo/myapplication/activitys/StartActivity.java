package com.example.stevengo.myapplication.activitys;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.stevengo.myapplication.*;
import com.example.stevengo.myapplication.base.UrlConsTable;
import com.example.stevengo.myapplication.entitys.ParameterLocation;
import com.example.stevengo.myapplication.services.InternetServiceLocation;
import com.example.stevengo.myapplication.services.InternetServiceLocation.GetLocation;

import com.example.stevengo.myapplication.utils.GeneralUtil;
import com.example.stevengo.myapplication.utils.LocationUtil;
import com.example.stevengo.myapplication.utils.PermissionsChecker;

/**
 * @author StevenGo
 * 闪屏，检测系统权限，初始化定位
 * */
public class StartActivity extends AppCompatActivity implements GetLocation {

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
    /**定位的服务*/
    private InternetServiceLocation internetServiceLocation;
    /**请求码*/
    private static final int REQUEST_CODE = 0;
    /**该屏幕停留的时间*/
    private static final int DELAY_MILLIONS=2*1000;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //打开主页面
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            //携带数据
            intent.putExtra("province",msg.getData().getCharSequence("province"));
            intent.putExtra("district",msg.getData().getCharSequence("district"));
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //加载布局
        setContentView(R.layout.activity_start);
        //初始权限检测器和定位服务
        mPermissionsChecker=new PermissionsChecker(this);
        //创建地理逆编码的服务
        internetServiceLocation=new InternetServiceLocation(this);
    }
    //加入权限判断和定位功能
    @Override
    protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(needPermissions)) {
            startPermissionsActivity();
        }else{
            //有操作权限时执行下面操作；获取位置
            Location location= LocationUtil.getLocation(getApplicationContext());
            //当位置不空的时候进行地理逆编码
            if(location!=null&& GeneralUtil.isInternetConnected(this)){
                String locationString=location.getLongitude()+","+location.getLatitude();
                internetServiceLocation.doGet(this,new ParameterLocation(UrlConsTable.LOCATION_KEY,locationString));
            }else{
                //当位置为空时显示提示
                Toast.makeText(this,"定位失败",Toast.LENGTH_SHORT).show();
                startActivity("未获取省份","未获取城市");
            }
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
            Toast.makeText(this, "缺少权限", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    @Override
    public void startActivity(String province, String district) {
        Message message=new Message();
        //携带数据
        Bundle bundle=new Bundle();
        bundle.putCharSequence("province",province);
        bundle.putCharSequence("district",district);
        message.setData(bundle);
        //停留2s后打开页面
        handler.sendMessageDelayed(message,DELAY_MILLIONS);
    }
}
