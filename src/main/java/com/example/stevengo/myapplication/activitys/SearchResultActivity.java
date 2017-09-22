package com.example.stevengo.myapplication.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.adapters.SearchResultAdapter;
import com.example.stevengo.myapplication.entitys.MusicInfo;
import com.example.stevengo.myapplication.utils.ReadXMLUtil;
import com.example.stevengo.myapplication.utils.TransformUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StevenGo on 2017/9/15.
 * 显示搜索结果的Acitivity
 */
public class SearchResultActivity extends AppCompatActivity {
    /**显示搜索内容的文本框*/
    private TextView mTextView;
    /**搜索结果界面*/
    private LinearLayout mLinearLayoutResult;
    /**索搜空界面*/
    private LinearLayout mLinearLayoutNoResult;
    /**搜索结果列表*/
    private ListView mListView;

    /**是否搜索到内容*/
    private boolean isResultExist;
    /**记录从XML文件中查到的数据*/
    private List<MusicInfo> mListContent;
    /**记录符合要求的数据*/
    private List<MusicInfo> mListSearchResult;
    /**搜索的关键字*/
    private String searchContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //初始化
        init();
        //根据查询结果修改界面显示
        alterView();
        //向listView添加自定义的view
        mListView.setAdapter(new SearchResultAdapter(this,mListSearchResult));
    }
    /**初始化*/
    private void init(){
        //界面初始化
        initView();
        mListContent = ReadXMLUtil.getMusic(SearchResultActivity.this);
//        isResultExist=getIntent().getBooleanExtra("isResultExist",false);
        searchContent=getIntent().getStringExtra("searchTextContent");
        //当搜索的结果不存在时将isResultExist设置为false
        mListSearchResult=getCheckMusic(searchContent);
        if(mListSearchResult.size()==0){
            isResultExist=false;
        }
        //当查询到记录的时候时候，将isResult的值置为true.
        else{
            isResultExist=true;
        }
    }
    /**初始化视图*/
    private void initView(){
        //根据id从布局文件中获取组件
        mTextView=(TextView)findViewById(R.id.search_text);
        mListView=(ListView)findViewById(R.id.search_listview_result);
        mLinearLayoutResult=(LinearLayout)findViewById(R.id.search_linearlayout_result);
        mLinearLayoutNoResult=(LinearLayout)findViewById(R.id.search_linearlayout_no_result);

        //设置组件的可见性，默认空界面不可见，有结果的可见，这里显示为空白
        mLinearLayoutResult.setVisibility(View.VISIBLE);
        mLinearLayoutNoResult.setSystemUiVisibility(View.GONE);
    }
    /**从所有数据中检索符合要求的记录*/
    public List<MusicInfo> getCheckMusic(String searchContent){
        //创建MusicInfo类型的引用
        MusicInfo music;
        //创建记录符合要求的list
        List<MusicInfo> checkedResult=new ArrayList<>();
        //将用户输入的内容转化为小写
        searchContent=searchContent.toLowerCase();
        //遍历从xml文件中读取到的内容
        for(int i=0;i<mListContent.size();i++){
            music=mListContent.get(i);
            //判断是否符合要求
            if(music.getName().indexOf(searchContent)!=-1|| TransformUtil.toPhonetic(music.getName()).indexOf(searchContent)!=-1||TransformUtil.toFirstPhonetic(music.getName()).indexOf(searchContent)!=-1||
                    music.getSinger().indexOf(searchContent)!=-1||TransformUtil.toPhonetic(music.getSinger()).indexOf(searchContent)!=-1||TransformUtil.toFirstPhonetic(music.getSinger()).indexOf(searchContent)!=-1){
                //符合要求时加入List里面
                checkedResult.add(music);
            }
        }
        return checkedResult;
    }
    /**根据查询结果修改视图*/
    private void alterView(){
        //判断是否查询到了数据，查到时显示查到的视图
        mTextView.setText(searchContent);
        if (isResultExist) {
            mLinearLayoutResult.setVisibility(View.VISIBLE);
            mLinearLayoutNoResult.setSystemUiVisibility(View.GONE);
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
