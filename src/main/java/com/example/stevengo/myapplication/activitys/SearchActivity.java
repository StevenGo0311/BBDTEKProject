package com.example.stevengo.myapplication.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.entitys.MusicInfo;
import com.example.stevengo.myapplication.services.DoHistoryServices;
import com.example.stevengo.myapplication.utils.ReadXMLUtil;
import com.example.stevengo.myapplication.utils.TransformUtil;
import com.example.stevengo.myapplication.views.FlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author StevenGo
 * 显示搜索歌曲的界面，包括搜索历史
 * */
public class SearchActivity extends AppCompatActivity {
    /**输入框*/
    private EditText mEditText;
    /**搜索按钮*/
    private Button mButton;
    /**清除按钮*/
    private Button mButtonClear;
    /**自定义的流式布局*/
    private FlowLayout mFlowLayout;
    /**设置TextVeiw的参数*/
    private LinearLayout.LayoutParams mLayoutParams;

//    /**从数据库里查找数据的工具*/
//    private SearchMusicDB mSearchMusicDB;
//    /**获取查到的数据*/
//    private Cursor mCursor;

    /**记录从XML文件中查到的数据*/
    private List<MusicInfo> mListContent;
    /**记录搜索的关键字*/
    private String editTextContent;
    /**处理历史记录*/
    private DoHistoryServices mDoHistoryServices;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.activity_search);
        //整体初始化
        init();
        //给搜索按钮添加单击监听器
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    startNewActivity(getCheckMusic(editTextContent),editTextContent);
                }
                //当输入的内容为空格时直接打印提示信息
                else {
                    Toast.makeText(SearchActivity.this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //为清除按钮添加单击事件
        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除历史记录
                mDoHistoryServices.clearHistory();
                //将当前已经显示的内容清除掉
                mFlowLayout.setVisibility(View.GONE);
                //件清除按钮设置为隐藏
                mButtonClear.setVisibility(view.GONE);
            }
        });
    }

    /**初始化*/
    private void init() {
        //创建操作历史记录的服务
        mDoHistoryServices = new DoHistoryServices(this);
        //初始化视图
        initView();
//        //创建数据库操作的对象
//        mSearchMusicDB=new SearchMusicDB(this);
        //从xml文件中读取数据
        mListContent = ReadXMLUtil.getMusic(SearchActivity.this);
    }
    /**初始化视图*/
    private void initView() {
        //根据id从布局文件中获取组件
        //输入框
        mEditText = (EditText) findViewById(R.id.search_edit_text);
        //搜素按钮
        mButton = (Button) findViewById(R.id.search_button_search);
        //清除按钮
        mButtonClear=(Button) findViewById(R.id.button_clear);
        //自定义的流式布局
        mFlowLayout = (FlowLayout) findViewById(R.id.search_history);
        //设置参数的对象
        mLayoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置边距
        mLayoutParams.setMargins(40,14,20,14);
        //添加历史记录
        addViewAdapter(mDoHistoryServices.readHistory());
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

    /**向自定义的流式布局中添加历史记录*/
    private void addViewAdapter(List<String> list){
        //定义TextView
        TextView textView;
        //清除容器中的所有内容
        mFlowLayout.removeAllViews();
        //根据查询到的历史记录添加组件
        for(int i=0;i<list.size();i++){
            //通过反射加载TexView
            textView=(TextView) LayoutInflater.from(this).inflate(R.layout.history_item,null);
            //设置TextView显示的内容
            textView.setText(list.get(i));
            //为textView设置布局参数
            textView.setLayoutParams(mLayoutParams);
            //将textView添加到容器中
            mFlowLayout.addView(textView);
            //为组件写单击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //启动新的界面
                    startNewActivity(getCheckMusic(((TextView)view).getText().toString()),((TextView)view).getText().toString());
                }
            });
        }
        //当没有历史记录的时候设置清除按钮不可见
        if(list.size()==0){
            mButtonClear.setVisibility(View.GONE);
        }
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
    public void startNewActivity(List<MusicInfo> searchResult,String searchText){
        //创建Intent
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        //向Intent添加要携带的数据
        intent.putExtra("searchResult", (Serializable) searchResult);
        intent.putExtra("searchTextContent",searchText);
        //启动新的activity
        startActivity(intent);
    }
}

