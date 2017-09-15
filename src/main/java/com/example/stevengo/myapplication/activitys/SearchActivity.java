package com.example.stevengo.myapplication.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.entitys.MusicInfo;
import com.example.stevengo.myapplication.utils.ReadXMLUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 显示搜索歌曲的界面
 * */
public class SearchActivity extends AppCompatActivity {
    /**输入框*/
    private EditText mEditText;
    /**搜索按钮*/
    private Button mButton;
    /**搜索结果界面*/
    private LinearLayout mLinearLayoutResult;
    /**索搜空界面*/
    private LinearLayout mLinearLayoutNoResult;

    /**搜索结果列表*/
    private ListView mListView;
    /**是否搜索到内容*/
    private boolean isResultExist;
    /**记录搜索到的内容*/
    private List<Map<String,Object>> mList;
//    /**从数据库里查找数据的工具*/
//    private SearchMusicDB mSearchMusicDB;
//    /**获取查到的数据*/
//    private Cursor mCursor;
    /**记录从XML文件中查到的数据*/
    private List<MusicInfo> mListContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.activity_search);
        //整体初始化
        init();
        //给搜索按钮添加单击监听器
        mButton.setOnClickListener(new View.OnClickListener() {
            //重写单击事件处理方法
            @Override
            public void onClick(View view) {
                //调用查询音乐的方法
                searchMusic();
            }
        });
    }
    /**初始化*/
    private void init(){
        //初始化试图
        initView();
//        //创建数据库操作的对象
//        mSearchMusicDB=new SearchMusicDB(this);
        //创建链表
        mList=new ArrayList<>();
    }
    /**
    /**初始化视图*/
    private void initView(){
        //根据id从布局文件中获取组件
        mEditText=(EditText)findViewById(R.id.search_edit_text);
        mButton=(Button)findViewById(R.id.search_button_search);
        mListView=(ListView)findViewById(R.id.search_listview_result);
        mLinearLayoutResult=(LinearLayout)findViewById(R.id.search_linearlayout_result);
        mLinearLayoutNoResult=(LinearLayout)findViewById(R.id.search_linearlayout_no_result);
        mEditText=(EditText)findViewById(R.id.search_edit_text);

        //设置组件的可见性，默认空界面不可见，有结果的可见，这里显示为空白
        mLinearLayoutResult.setVisibility(View.VISIBLE);
        mLinearLayoutNoResult.setSystemUiVisibility(View.GONE);
    }
    /**通过调用数据库操作对象的方法，获取查询数据*/
//    private void  searchResult(){
//        String searchText="";
//        //获取文本框中用户的输入
//        searchText=mEditText.getText().toString().trim();
//        //检查用户的输入是否为空，空的情况下直接显示查不到结果
//        if(searchText.equals("")){
//            isResultExist=false;
//        }
//        else{
//            //查询数据
//            mCursor=mSearchMusicDB.searchMusicFromTable(searchText);
//            //清理链表中的内容
//            mList.clear();
//            //获取查询到Cursor中的内容，并将其添加到链表中
//            while(mCursor.moveToNext()){
//                Map<String,Object> map=new HashMap<>();
//                map.put("musicName",mCursor.getString(0));
//                mList.add(map);
//            }
//            //判断是否找到了数据
//            if(mList.size()==0){
//                isResultExist=false;
//            }
//            else{
//                isResultExist=true;
//            }
//        }
//    }
    /**从xml文件中获取数据*/
    private void searchResultXML(){
        String searchText="";
        //获取文本框中用户的输入
        searchText=mEditText.getText().toString().trim();
        //检查用户的输入是否为空，空的情况下直接显示查不到结果
        if(searchText.equals("")){
            isResultExist=false;
        }
        else{
            //查询数据
            mListContent= ReadXMLUtil.getMusic(this,searchText);
            //清理链表中的内容
            mList.clear();
            //获取查询到mListContent中的内容，并将其添加到链表中
            for(int i=0;i<mListContent.size();i++){
                Map<String,Object> map=new HashMap<>();
                map.put("musicName",mListContent.get(i).getName());
                mList.add(map);
            }
            //判断是否找到了数据
            if(mList.size()==0){
                isResultExist=false;
            }
            else{
                isResultExist=true;
            }
        }
    }
    /**根据查询结果修改视图*/
    private void searchMusic(){
//        searchResult();
        //从xml文件中获取数据
        searchResultXML();
        //判断是否查询到了数据，查到时显示查到的视图
        if (isResultExist) {
            mLinearLayoutResult.setVisibility(View.VISIBLE);
            mLinearLayoutNoResult.setSystemUiVisibility(View.GONE);
            //创建Adapter
            SimpleAdapter result = new SimpleAdapter(getApplicationContext(), mList, R.layout.search_sesult_item, new String[]{"musicName"}, new int[]{R.id.textview_search_result});
            //添加Adapter
            mListView.setAdapter(result);
        }
        else {
            //显示没有内容的界面
            mLinearLayoutResult.setVisibility(View.GONE);
            mLinearLayoutNoResult.setSystemUiVisibility(View.VISIBLE);
            //提示没有索搜到内容
            Toast.makeText(getApplicationContext(), "搜索内容不存在", Toast.LENGTH_SHORT).show();
        }
    }
}
