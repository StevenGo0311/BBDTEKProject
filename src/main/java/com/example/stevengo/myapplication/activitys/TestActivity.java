package com.example.stevengo.myapplication.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.entitys.MusicEntity;
import com.example.stevengo.myapplication.entitys.Parameter;
import com.example.stevengo.myapplication.utils.InternetUtilRetrofit;

import java.util.List;

public class TestActivity extends AppCompatActivity {
    List<MusicEntity.TracksBean> tracksBean;
    Handler hander=new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
//        List<MusicInfo> readXMLUtil= com.example.stevengo.myapplication.utils.ReadXMLUtil.getMusic(this,"qh");
//        for(int i=0;i<readXMLUtil.size();i++){
//            Log.d("StevenGo",readXMLUtil.get(i).getName());
//        }
        new Thread(){
            @Override
            public void run() {
                tracksBean =InternetUtilRetrofit.doGet(new Parameter("二胡",1,1));
                for (int i = 0; i <tracksBean.size() ; i++) {
                    Log.d("StevenGo",tracksBean.get(i).getTitle());
                }
                hander.sendEmptyMessage(0);
            }
        }.start();

    }

}
