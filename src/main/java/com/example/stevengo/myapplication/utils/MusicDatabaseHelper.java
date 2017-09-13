package com.example.stevengo.myapplication.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by StevenGo on 2017/9/12.
 * 自定义SQLiteOpenHelper类，重写构造方法和onCreate方法
 */

public class MusicDatabaseHelper extends SQLiteOpenHelper {
    //创建音乐表的语句
    final String SQL_CREATE_TABLE="create table musicTable(music_name varchar(28))";
    //向表中插入数据的语句
    final String SQL_INSERT_DATA="insert into musicTable values(?)";
    //存放插入的数据
    private String[] music;
    /**重写构造方法，初始化插入的数据*/
    public MusicDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        music=new String[]{
                "告白气球",
                "晴天",
                "稻香",
                "双截棍",
                "青花瓷",
                "给我一首歌的时间",
                "霍元甲",
                "听妈妈的话",
                "七里香",
                "简单爱",
                "牛仔很忙",
                "夜曲",
                "屋顶",
                "算什么男人",
                "烟花易冷",
                "彩虹",
                "红尘客栈",
                "菊花台"
        };
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
//        Log.d("StevenGo","创建表的操作已执行");
        //用循环语句向数据库插入数据
        for(int i=0;i<music.length;i++){
            sqLiteDatabase.execSQL(SQL_INSERT_DATA,new String[]{music[i]});
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
