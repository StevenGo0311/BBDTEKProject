package com.example.stevengo.myapplication.adapters;

import android.content.Context;
import android.util.Log;
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
    public void onDataChange(List<MusicInfo> list){
        this.mListMusicInfo=list;
        this.notifyDataSetChanged();
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
        //创建实体
        MusicInfo musicInfo=mListMusicInfo.get(i);
        ViewHolder viewHolder;
        if(view==null){
            viewHolder=new ViewHolder();
            //从资源文件中读取View
            view=LayoutInflater.from(mContext).inflate(R.layout.search_result_item,null);
            viewHolder.textView=(TextView) view.findViewById(R.id.textview_search_result);
            view.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder) view.getTag();

        }
        //将音乐设置到textView
        viewHolder.textView.setText(musicInfo.getName());
        return view;
    }
    //定义组件持有类，防止多次从资源文件读取布局
    class ViewHolder{
        TextView textView;
    }
}
