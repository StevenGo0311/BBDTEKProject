package com.example.stevengo.myapplication.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.adapters.SearchResultAdapter;
import com.example.stevengo.myapplication.entitys.MusicInfo;
import com.example.stevengo.myapplication.utils.InternetUtil;
import com.example.stevengo.myapplication.utils.ReadXMLUtil;
import com.example.stevengo.myapplication.views.SearchResultListView;
import com.example.stevengo.myapplication.views.SearchResultListView.IRefreshListener;

import java.util.List;

/**
 * Created by StevenGo on 2017/9/15.
 * 显示搜索结果的Acitivity
 */
public class SearchResultActivity extends AppCompatActivity implements IRefreshListener,SearchResultListView.ILoadListener{
    /**显示搜索内容的文本框*/
    private TextView mTextView;
    /**搜索结果界面*/
    private LinearLayout mLinearLayoutResult;
    /**索搜空界面*/
    private LinearLayout mLinearLayoutNoResult;
    /**搜索结果列表*/
    private SearchResultListView mListView;

    /**是否搜索到内容*/
    private boolean isResultExist;
    /**记录从XML文件中查到的数据*/
    private List<MusicInfo> mListContent;
    /**记录符合要求的数据*/
    private List<MusicInfo> mListSearchResult;
    /**网络是否可用*/
    private boolean mIsInternetValid;
    /**搜索的关键字*/
    private String searchContent;
    /**加载网络数据时的进度条*/
    private AlertDialog alertDialog;
    /**自定义适配器*/
    private SearchResultAdapter searchResultAdaptar;

    /**Url*/
    private String url;
    /**关键字*/
    private String kw;
    /**页数*/
    private int pi;
    /**页大小*/
    private int pz;
    /**操作方式*/
    private int operationForm;
    /**加载更多*/
    private final int LOAD=1;
    /**刷新*/
    private final int REFRESH=2;
    /**初始化*/
    private final int INIT=3;
    /**标志读取网络数据开始和结束*/
    private final int START_REFRESH=0x1;
    private final int FINISH_REFRESH=0X2;
    /**更新UI的操作*/
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //判断msg的what,决定执行什么操作
            switch (msg.what){
                //显示对话框
                case START_REFRESH:
                    alertDialog.show();
                    break;
                //1.隐藏对话框，2.更新视图 3.添加自定义适配器 4.通知listView完成操作
                case FINISH_REFRESH:
                    //1
                    alertDialog.dismiss();
                    //2
                    alterView();
                    //3
                    if(searchResultAdaptar ==null){
                        searchResultAdaptar =new SearchResultAdapter(getApplicationContext(),mListSearchResult);
                        mListView.setAdapter(searchResultAdaptar);
                    }else{
                        searchResultAdaptar.onDataChange(mListSearchResult);
                    }
                    //4
                    mListView.refreshComplete();
                    mListView.loadComplete();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        //初始化
        init();
//        //根据查询结果修改界面显示
//        alterView();
        //向listView添加自定义的view
        operationForm=INIT;
        startSearchThread();

    }
    /**初始化*/
    private void init(){
        //界面初始化
        initView();
        //mListContent = ReadXMLUtil.getMusic(SearchResultActivity.this);
//        isResultExist=getIntent().getBooleanExtra("isResultExist",false);
        //读取用户输入的内容
        searchContent=getIntent().getStringExtra("searchTextContent");
        mIsInternetValid=getIntent().getBooleanExtra("isInternetValid",false);
        //Log.d("StevenGo", "mIsInternetValid: "+mIsInternetValid);
        mTextView.setText(searchContent);
        //给url,kw,pi,pz赋值
        url="http://v5.pc.duomi.com/search-ajaxsearch-searchall";
        kw=searchContent;
        pi=1;
        pz=10;
//        //当搜索的结果不存在时将isResultExist设置为false
//        mListSearchResult=getCheckMusic(searchContent);
//        if(mListSearchResult.size()==0){
//            isResultExist=false;
//        }
//        //当查询到记录的时候时候，将isResult的值置为true.
//        else{
//            isResultExist=true;
//        }
    }
    /**初始化视图*/
    private void initView(){
        //根据id从布局文件中获取组件
        mTextView=(TextView)findViewById(R.id.search_text);
        mListView=(SearchResultListView)findViewById(R.id.search_listview_result);
        mLinearLayoutResult=(LinearLayout)findViewById(R.id.search_linearlayout_result);
        mLinearLayoutNoResult=(LinearLayout)findViewById(R.id.search_linearlayout_no_result);
        //新建对话框
        alertDialog=new AlertDialog.Builder(SearchResultActivity.this).create();
        //通过反射加载对话框的显示内容
        alertDialog.setView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_progress,null));
        //将这个类的对象传给ListView作为回调接口
        mListView.setInterfaceRefresh(this);
        mListView.setInterfaceLoad(this);
        //设置组件的可见性，默认空界面不可见，有结果的可见，这里显示为空白
        mLinearLayoutResult.setVisibility(View.VISIBLE);
        mLinearLayoutNoResult.setSystemUiVisibility(View.GONE);
    }
//    /**从所有数据中检索符合要求的记录*/
//    public List<MusicInfo> getCheckMusic(String searchContent){
//        //创建MusicInfo类型的引用
//        MusicInfo music;
//        //创建记录符合要求的list
//        List<MusicInfo> checkedResult=new ArrayList<>();
//        //将用户输入的内容转化为小写
//        searchContent=searchContent.toLowerCase();
//        //遍历从xml文件中读取到的内容
//        for(int i=0;i<mListContent.size();i++){
//            music=mListContent.get(i);
//            //判断是否符合要求
//            if(music.getName().indexOf(searchContent)!=-1|| TransformUtil.toPhonetic(music.getName()).indexOf(searchContent)!=-1||TransformUtil.toFirstPhonetic(music.getName()).indexOf(searchContent)!=-1||
//                    music.getSinger().indexOf(searchContent)!=-1||TransformUtil.toPhonetic(music.getSinger()).indexOf(searchContent)!=-1||TransformUtil.toFirstPhonetic(music.getSinger()).indexOf(searchContent)!=-1){
//                //符合要求时加入List里面
//                checkedResult.add(music);
//            }
//        }
//        return checkedResult;
//    }
    /**根据查询结果修改视图*/
    private void alterView(){
        //判断是否查询到了数据，查到时显示查到的视图
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
    /**启动新线程，在互联网上查找数据*/
    public void startSearchThread(){
        //判断网络是否可用
        if(mIsInternetValid){
            //创建新线程，重写run方法
            new Thread(){
                @Override
                public void run() {
                    //向Handler发送消息开始查找数据
                    Message messageStart=new Message();
                    messageStart.what=START_REFRESH;
                    mHandler.sendMessage(messageStart);
                    //如果操作形式是初始化或者刷新时执行下列操作
                    if(operationForm==INIT||operationForm==REFRESH){
                        //从第一页开始读取数据
                        pi=1;
                        mListSearchResult=InternetUtil.doGet(url,kw,pi,pz);
                    }else{
                        //将读取的所有数据连接到一起
                        mListSearchResult.addAll(InternetUtil.doGet(url,kw,pi,pz));
                    }
                   // Log.d("StevenGo", "run: "+mListSearchResult.size());
                    //判断是否查询到了数据
                    if(mListSearchResult.size()==0){
                        isResultExist=false;
                    }
                    //当查询到记录的时候时候，将isResult的值置为true.
                    else{
                        isResultExist=true;
                    }
                    //通知Handler数据查询结束
                    Message messageFinish=new Message();
                    messageFinish.what=FINISH_REFRESH;
                    mHandler.sendMessage(messageFinish);
                }
            }.start();
        }else{
            Toast.makeText(this, "哎呀，网络好像有毛病了~", Toast.LENGTH_SHORT).show();
        }
    }
    /**重写接口中定义的方法写入具体操作*/
    @Override
    public void onRefresh() {
        //将操作形式设置为刷新，启动新线程从互联网读取数据
        operationForm=REFRESH;
        startSearchThread();
    }

    @Override
    public void onLoad() {
        operationForm=LOAD;
        //读取下一页数据
        pi++;
        startSearchThread();
    }
}
