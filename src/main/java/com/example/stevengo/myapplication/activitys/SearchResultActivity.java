package com.example.stevengo.myapplication.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stevengo.myapplication.R;

import java.util.List;
import java.util.Map;

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
    /**记录搜索到的内容*/
    private List<Map<String,Object>> mList;
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
    }
    /**初始化*/
    private void init(){
        //界面初始化
        initView();
        //获取Intent携带的数据
        mList=(List)getIntent().getSerializableExtra("searchResult");
        isResultExist=getIntent().getBooleanExtra("isResultExist",false);
        searchContent=getIntent().getStringExtra("searchTextContent");
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
