package com.example.stevengo.myapplication.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.listeners.SearchButtonOnClickListener;
/**显示搜索功能的界面*/
public class SearchActivity extends AppCompatActivity {
    /**输入框*/
    private EditText mEditText;
    /**搜索按钮*/
    private Button mButton;
    /**搜索结果列表*/
    private ListView mListView;
    /**搜索结果界面*/
    private LinearLayout mLinearLayoutResult;
    /**索搜空界面*/
    private LinearLayout mLinearLayoutNoResult;
    /**按钮监听器*/
    private SearchButtonOnClickListener mSearchButtonOnClickListener;
    /**是否搜索到内容*/
    private boolean isResultExist;
    /**创建消息处理器*/
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.activity_search);
        //初始化试图
        initView();
        //创建消息处理器
        createHandler();
        //初始化单机监听器
        initListener();
        //为按钮添加监听器
        mButton.setOnClickListener(mSearchButtonOnClickListener);

    }
    /**初始化视图*/
    private void initView(){
        //根据id从布局文件中获取组件
        mEditText=(EditText)findViewById(R.id.search_edit_text);
        mButton=(Button)findViewById(R.id.search_button_search);
        mListView=(ListView)findViewById(R.id.search_listview_result);
        mLinearLayoutResult=(LinearLayout)findViewById(R.id.search_linearlayout_result);
        mLinearLayoutNoResult=(LinearLayout)findViewById(R.id.search_linearlayout_no_result);
        //设置组件的可见性，默认空界面不可见，有结果的可见，这里显示为空白
        mLinearLayoutResult.setVisibility(View.VISIBLE);
        mLinearLayoutNoResult.setSystemUiVisibility(View.GONE);
    }
    /**初始化监听器*/
    private void initListener(){
        //实例化自定义的监听器
        mSearchButtonOnClickListener=new SearchButtonOnClickListener(this,mEditText,mHandler);
    }
    /**创建消息处理器*/
    private void createHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x102) {
                    //搜索的内容是否存在
                    isResultExist = mSearchButtonOnClickListener.isResultExist();
                    //当内容存在的时候，界面切换到有内容的那个，然后把内容显示出来；
                    //当内容不存在的时候，把界面切换到不存在的那一页，提示没搜到内容
                    if (isResultExist) {
                        mLinearLayoutResult.setVisibility(View.VISIBLE);
                        mLinearLayoutNoResult.setSystemUiVisibility(View.GONE);
                        //创建Adapter
                        SimpleAdapter result = new SimpleAdapter(getApplicationContext(), mSearchButtonOnClickListener.getSearchtResult(), R.layout.search_sesult_item, new String[]{"musicName"}, new int[]{R.id.textview_search_result});
                        //添加Adapter
                        mListView.setAdapter(result);
                    }
                    else {
//                        mLinearLayoutResult.setVisibility(View.VISIBLE);
//                        mLinearLayoutNoResult.setSystemUiVisibility(View.GONE);
                        //显示没有内容的界面
                        mLinearLayoutResult.setVisibility(View.GONE);
                        mLinearLayoutNoResult.setSystemUiVisibility(View.VISIBLE);
                        //提示没有索搜到内容
                        Toast.makeText(getApplicationContext(), "搜索内容不存在", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        };
    }


}
