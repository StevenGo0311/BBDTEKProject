package com.example.stevengo.myapplication.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stevengo.myapplication.R;
import com.example.stevengo.myapplication.adapters.SearchResultAdapter;
import com.example.stevengo.myapplication.entitys.MusicEntity;
import com.example.stevengo.myapplication.entitys.Parameter;
import com.example.stevengo.myapplication.services.InternetServiceRetrofit;
import com.example.stevengo.myapplication.utils.GeneralUtil;
import com.example.stevengo.myapplication.views.SearchResultListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *显示结果的fragment
 */
public class SearchResultFragment extends Fragment implements
        InternetServiceRetrofit.LoadMusic,SearchResultListView.IRefreshListener,SearchResultListView.ILoadListener {
    /**显示搜索内容的文本框*/
    @BindView(R.id.search_text) TextView mTextView;
    /**索搜空界面*/
    @BindView(R.id.search_no_result_textView) TextView mTextViewSearchNoresult;
    /**搜索结果列表*/
    @BindView(R.id.search_listview_result) SearchResultListView mListView;

    /**是否搜索到内容*/
    private boolean isResultExist;
    /**记录符合要求的数据*/
    private List<MusicEntity.TracksBean> mListSearchResult;
    /**搜索的关键字*/
    private String searchContent;
    /**自定义适配器*/
    private SearchResultAdapter searchResultAdaptar;

    /**请求api的参数*/
    private Parameter parameter;
    /**操作方式*/
    private int operationForm;
    /**加载更多*/
    private final int LOAD=1;
    /**刷新*/
    private final int REFRESH=2;
    /**初始化*/
    private final int INIT=3;
    /**对话框*/
    private AlertDialog dialogProgress;
    /**读取网络照片的*/
    private InternetServiceRetrofit internetServiceRetrofit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search_result,null);
        ButterKnife.bind(this,view);
        internetServiceRetrofit=new InternetServiceRetrofit();
        //初始化视图
        initView();
        parameter=new Parameter(searchContent,1,10);
        //将操作方式定义为初始化
        operationForm=INIT;
        //从网络读取音乐信息
        getMusic();
        return view;
    }
    /**初始化视图*/
    private void initView(){
        //设置组件的可见性，默认空界面可见，有结果的不可见，这里显示为空白
        alterView();
        //读取用户输入的内容
        searchContent=getArguments().getString("searchTextContent");
        mTextView.setText(searchContent);
        //创建一个对话框
        dialogProgress=new AlertDialog.Builder(getActivity())
                .setView(LayoutInflater.from(getActivity()).inflate(R.layout.dialog_progress,null))
                .create();
        //将这个类的对象传给ListView作为回调接口
        mListView.setInterfaceRefresh(this);
        mListView.setInterfaceLoad(this);
        //将本类的对象传给internetServiceRetrofit
        internetServiceRetrofit.setLoadMusic(this);
    }
    /**根据查询结果修改视图*/
    private void alterView(){
        //判断是否查询到了数据，查到时显示查到的视图
        if (isResultExist) {
            mListView.setVisibility(View.VISIBLE);
            mTextViewSearchNoresult.setVisibility(View.GONE);
        }
        else {
            //显示没有内容的界面
            mListView.setVisibility(View.GONE);
            mTextViewSearchNoresult.setVisibility(View.VISIBLE);
        }
    }
    /**给组件绑定单击事件*/
    @OnClick({R.id.search_text,R.id.search_no_result_textView})
    public void buttonOnClick(View view){
        //判断事件源，执行相应的操作
        switch (view.getId()){
            //结束本activity
            case R.id.search_text:
                getFragmentManager().popBackStack();
                break;
            //刷新
            case R.id.search_no_result_textView:
                onRefresh();
                break;
        }
    }
    /**从互联网上获取音乐信息*/
    public void getMusic(){
        //首先判断网络是否可用，网络可用时进行搜索，不可用时修改界面，弹出提示
        if(GeneralUtil.isInternetConnected(getActivity())){
            isResultExist=true;
            internetServiceRetrofit.doGet(parameter);
            alterView();

        }else{
            isResultExist=false;
            alterView();
            Toast.makeText(getActivity(), "网络貌似不能用~", Toast.LENGTH_SHORT).show();
        }
    }

    /**重写接口中定义的方法写入具体操作*/
    @Override
    public void onRefresh() {
        //将操作形式设置为刷新，启动新线程从互联网读取数据
        operationForm=REFRESH;
        getMusic();
    }
    /**重写接口中定义的方法写入具体操作*/
    @Override
    public void onLoad() {
        operationForm=LOAD;
        //读取下一页数据
        parameter.setPi(parameter.getPi()+1);
        getMusic();
    }
    /**开始读取网络数据时显示对话框阻塞主进程*/
    @Override
    public void loadStart() {
        dialogProgress.show();
    }
    /**读取结束以后的操作*/
    @Override
    public void loadFinish(List<MusicEntity.TracksBean> tracksBeen) {
        //1.取消阻塞进程
        dialogProgress.dismiss();
        //2.判断操作类型，获取数据
        // 如果是初始化或者刷新，直接获取网络数据
        //如果是加载更多，将之前的数据和新得到的数据拼接
        if(operationForm==INIT||operationForm==REFRESH){
            mListSearchResult=tracksBeen;
        }else{
            mListSearchResult.addAll(tracksBeen);
        }
        //3.根据结果修改界面显示
        if(mListSearchResult.size()==0){
            isResultExist=false;
        }
        //当查询到记录的时候时候，将isResult的值置为true.
        else{
            isResultExist=true;
        }
        alterView();
        //为listView设置内容
        if(searchResultAdaptar ==null){
            searchResultAdaptar =new SearchResultAdapter(getActivity(),mListSearchResult);
            mListView.setAdapter(searchResultAdaptar);
        }else{
            searchResultAdaptar.onDataChange(mListSearchResult);
        }
        //4.通知listView刷新或者加载完成
        mListView.refreshComplete();
        mListView.loadComplete();
    }

}
