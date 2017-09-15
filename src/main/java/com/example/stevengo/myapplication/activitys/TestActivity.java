package com.example.stevengo.myapplication.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.stevengo.myapplication.entitys.MusicInfo;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        List<MusicInfo> readXMLUtil= com.example.stevengo.myapplication.utils.ReadXMLUtil.getMusic(this,"qh");
        for(int i=0;i<readXMLUtil.size();i++){
            Log.d("StevenGo",readXMLUtil.get(i).getName());
        }

    }

}
