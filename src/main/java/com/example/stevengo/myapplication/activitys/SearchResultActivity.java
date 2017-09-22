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
    //搜索的结果
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
        //获取Intent携带的数据
        mListSearchResult=(List)getIntent().getSerializableExtra("searchResult");
//        isResultExist=getIntent().getBooleanExtra("isResultExist",false);
        searchContent=getIntent().getStringExtra("searchTextContent");
        //当搜索的结果不存在时将isResultExist设置为false
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
