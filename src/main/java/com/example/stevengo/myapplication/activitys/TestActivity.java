package com.example.stevengo.myapplication.activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.stevengo.myapplication.entitys.MusicInfo;
import com.example.stevengo.myapplication.utils.*;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        List<MusicInfo> readXMLUtil= com.example.stevengo.myapplication.utils.ReadXMLUtil.GET_MUSIC(this,"qh");
        for(int i=0;i<readXMLUtil.size();i++){
            Log.d("StevenGo",readXMLUtil.get(i).getName());
        }

    }

}
