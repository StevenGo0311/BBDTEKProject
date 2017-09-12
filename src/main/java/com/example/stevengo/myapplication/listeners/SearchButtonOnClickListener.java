package com.example.stevengo.myapplication.listeners;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.stevengo.myapplication.db.MusicDatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by StevenGo on 2017/9/12.
 * 自定义单击按钮的监听器，
 */

public class SearchButtonOnClickListener implements View.OnClickListener {
    /**接收调用者传过来的文本框*/
    private EditText searchEditText;
    /**数据库*/
    private MusicDatabaseHelper mMusicDatabaseHelper;
    /**查询结果集*/
    private Cursor mCursor;
    /**用链表对查找到的数据进行封装*/
    private List<Map<String,Object>> searchtResult;
    /**记录是否查找到了数据*/
    private boolean isResultExist;
    /**接收消息处理器*/
    private Handler handler;
    /**查询语句*/
    final String SQL_SELECT_MUSIC="select * from musicTable where music_name like ?";
    /**重写构造方法，对成员变量进行初始化*/
    public SearchButtonOnClickListener(Context context, EditText editText, Handler handler){
        searchEditText=editText;
        this.handler=handler;
        //创建数据库
        mMusicDatabaseHelper=new MusicDatabaseHelper(context,"musicDatabase",null,1);
        searchtResult=new ArrayList<>();
    }
    /**重新onClick方法*/
    @Override
    public void onClick(View view) {
        //Log.d("StevenGo","单击按钮");
        //获取文本框中的内容，并取消掉多余的空格
        String searchContent=searchEditText.getText().toString().trim();
        //当输入内容为空的时候什么也不做
        if(searchContent.equals("")){
            return;
        }
        //执行查询语句
        mCursor=mMusicDatabaseHelper.getReadableDatabase().rawQuery(SQL_SELECT_MUSIC,new String[]{"%"+searchContent+"%"});
        //清除上次查询结果
        searchtResult.clear();
        //取查询到的结果，将其存放在一个链表里面
        while(mCursor.moveToNext()){
            Map<String,Object> map=new HashMap<>();
            map.put("musicName",mCursor.getString(0));
            searchtResult.add(map);
        }
        //判断是否查到了结果
        if(searchtResult.size()==0){
            isResultExist=false;
        }
        else{
            isResultExist=true;
        }
        //创建一则消息，并将其发送给处理器
        Message message=new Message();
        message.what=0x102;
        handler.sendMessage(message);
        //Log.d("StevenGo","搜索内容为"+searchContent);
        //Log.d("StevenGo","取得数据的长度为"+searchtResult.size());
    }

    public List<Map<String, Object>> getSearchtResult() {
        return searchtResult;
    }

    public boolean isResultExist() {
        return isResultExist;
    }
}
