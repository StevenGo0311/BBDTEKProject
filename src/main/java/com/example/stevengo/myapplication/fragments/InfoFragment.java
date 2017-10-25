package com.example.stevengo.myapplication.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stevengo.myapplication.R;

/**
 * 显示资讯的Fragment
 */
public class InfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //获取布局文件
        View view=inflater.inflate(R.layout.fragment_info,null);
        return view;
    }


}
