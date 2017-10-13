package com.example.stevengo.myapplication.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.services.DoHistoryServices;
import com.example.stevengo.myapplication.views.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author StevenGo
 * 显示搜索歌曲的界面，包括搜索历史
 * */
public class SearchActivity extends AppCompatActivity {
    //通过注解绑定组件
    /**输入框*/
    @BindView(R.id.search_edit_text) EditText mEditText;
    /**搜索按钮*/
    @BindView(R.id.search_button_search) Button mButton;
    /**清除按钮*/
    @BindView(R.id.button_clear) Button mButtonClear;
    /**自定义的流式布局*/
    @BindView(R.id.search_history) FlowLayout mFlowLayout;

    /**记录搜索的关键字*/
    private String editTextContent;
    /**处理历史记录*/
    private DoHistoryServices mDoHistoryServices;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        //创建操作历史记录的服务
        mDoHistoryServices = new DoHistoryServices(this);
        addViewAdapter(mDoHistoryServices.readHistory());
    }

    /**向自定义的流式布局中添加历史记录*/
    private void addViewAdapter(List<String> list){
        //定义TextView
        TextView textView;
        //清除容器中的所有内容
        mFlowLayout.removeAllViews();
        //根据查询到的历史记录添加组件
        for(int i=0;i<list.size();i++){
            //通过反射加载TexView
            View linearLayout=LayoutInflater.from(this).inflate(R.layout.history_item,null);
            textView=(TextView) linearLayout.findViewById(R.id.history_item_id);
            //设置TextView显示的内容
            textView.setText(list.get(i));
            //将textView添加到容器中
            mFlowLayout.addView(linearLayout);
            //为组件写单击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //启动新的界面
                    startNewActivity(((TextView)view).getText().toString());
                }
            });
        }
        //当没有历史记录的时候设置清除按钮不可见
        if(list.size()==0){
            mButtonClear.setVisibility(View.GONE);
        }
    }
    public void startNewActivity(String searchText){
        //创建Intent
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        //向Intent添加要携带的数据
        intent.putExtra("searchTextContent",searchText);
        //启动新的activity
        startActivity(intent);
    }
    @OnClick(R.id.search_button_search)
    public void search(View view){
        //获取输入框内容，并给去掉多余的空格
        editTextContent = mEditText.getText().toString().trim();
        //判断搜索的内容是否为空格
        if (!editTextContent.equals("")) {
            //将搜索的内容写入历史记录
            mDoHistoryServices.writeHistory(editTextContent);
            //添加历史记录
            addViewAdapter(mDoHistoryServices.readHistory());
            //将清除按钮设置为可见
            mButtonClear.setVisibility(View.VISIBLE);
            //刷新显示历史记录的界面
            startNewActivity(editTextContent);
        }
        //当输入的内容为空格时直接打印提示信息
        else {
            Toast.makeText(SearchActivity.this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.button_clear)
    public void clearDistory(View view) {
        //删除历史记录
        mDoHistoryServices.clearHistory();
        //将当前已经显示的内容清除掉
        mFlowLayout.setVisibility(View.GONE);
        //件清除按钮设置为隐藏
        mButtonClear.setVisibility(view.GONE);
    }
}

