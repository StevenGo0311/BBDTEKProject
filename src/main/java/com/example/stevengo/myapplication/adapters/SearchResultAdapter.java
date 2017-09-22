package com.example.stevengo.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.entitys.MusicInfo;

import java.util.List;

/**
 * @author StevenGo
 * 自定义的Adapter，向ListView中填充内容
 */
public class SearchResultAdapter extends BaseAdapter {
    /**上下文*/
    private Context mContext;
    /**要显示的bean*/
    private List<MusicInfo> mListMusicInfo;
    /**构造方法，初始化成员变量*/
    public SearchResultAdapter(Context context, List<MusicInfo> musicInfo){
        this.mContext=context;
        this.mListMusicInfo=musicInfo;
    }
    /**重写返回列表项的数量的方法*/
    @Override
    public int getCount() {
        return mListMusicInfo.size();
    }
    /**重写返回列表项的方法*/
    @Override
    public Object getItem(int i) {
        return mListMusicInfo.get(i);
    }
    /**重写返回ID的方法*/
    @Override
    public long getItemId(int i) {
        return i;
    }
    /**重写显示item的方法*/
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //通过反射取得布局文件
        View linearLayout= LayoutInflater.from(mContext).inflate(R.layout.search_result_item,null);
        //取得布局文件中的view
        TextView textView=(TextView) linearLayout.findViewById(R.id.textview_search_result);
        //设置其text
        textView.setText(mListMusicInfo.get(i).getName());
        //返回linearLayout
        return linearLayout;
    }
}
