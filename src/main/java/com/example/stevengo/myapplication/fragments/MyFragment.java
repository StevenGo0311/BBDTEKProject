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
 * 显示我的的fragment
 */
public class MyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //加载布局
        View view = inflater.inflate(R.layout.fragment_my, null);
        return view;
    }

}
