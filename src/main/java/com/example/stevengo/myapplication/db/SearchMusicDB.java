package com.example.stevengo.myapplication.db;

import android.content.Context;
import android.database.Cursor;

import com.example.stevengo.myapplication.utils.MusicDatabaseHelper;

/**
 * Created by StevenGo on 2017/9/13.
 * 定义工具，用于根据用户输入从数据库库中查询数据
 */
public class SearchMusicDB {
    /**
     * 数据库处理类
     */
    private MusicDatabaseHelper mMusicDatabaseHelper;
    /**
     * 上下文，接受构造方法中的参数
     */
    private Context mContext;
    /**
     * 查询结果
     */
    private Cursor mCursor;
    /**
     * 查询语句
     */
    final String SQL_SELECT_MUSIC = "select * from musicTable where music_name like ?";

    /**
     * 构造方法，接受参数，初始化数据库处理类
     */
    public SearchMusicDB(Context context) {
        mContext = context;
        //设置数据库名称为musicDatabase
        mMusicDatabaseHelper = new MusicDatabaseHelper(mContext, "musicDatabase", null, 1);
    }

    /**
     * 查询数据
     */
    public Cursor searchMusicFromTable(String searchContent) {
        mCursor = mMusicDatabaseHelper.getReadableDatabase().rawQuery(SQL_SELECT_MUSIC, new String[]{"%" + searchContent + "%"});
        return mCursor;
    }
}
