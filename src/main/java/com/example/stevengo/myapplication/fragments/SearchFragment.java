package com.example.stevengo.myapplication.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by StevenGo on 2017/10/20.
 * 搜索的fragment
 */

public class SearchFragment extends Fragment {
    //通过注解绑定组件
    /**输入框*/
    @BindView(R.id.search_edit_text)
    EditText mEditText;
    /**搜索按钮*/
    @BindView(R.id.search_button_search)
    Button mButton;
    /**清除按钮*/
    @BindView(R.id.button_clear) Button mButtonClear;
    /**自定义的流式布局*/
    @BindView(R.id.search_history) FlowLayout mFlowLayout;

    private FragmentManager fragmentManager;

    /**记录搜索的关键字*/
    private String editTextContent;
    /**处理历史记录*/
    private DoHistoryServices mDoHistoryServices;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager=getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search,null);
        ButterKnife.bind(this,view);
        //创建操作历史记录的服务
        mDoHistoryServices = new DoHistoryServices(getActivity());
        addViewAdapter(mDoHistoryServices.readHistory());
        return view;
    }
    /**向自定义的流式布局中添加历史记录*/
    private void addViewAdapter(List<String> list){
        Log.d("StevenGo","list的长度"+list.size());
        //定义TextView
        TextView textView;
        //清除容器中的所有内容
        mFlowLayout.removeAllViews();
        //根据查询到的历史记录添加组件
        for(int i=0;i<list.size();i++){
            //通过反射加载TexView
            View linearLayout=LayoutInflater.from(getActivity()).inflate(R.layout.history_item,null);
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
                    replaceNewFragment(((TextView)view).getText().toString());
                }
            });
        }
        //当没有历史记录的时候设置清除按钮不可见
        if(list.size()==0){
            mButtonClear.setVisibility(View.GONE);
        }
    }
    public void replaceNewFragment(String searchText){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        SearchResultFragment searchResultFragment=new SearchResultFragment();
        Bundle bundle=new Bundle();
        bundle.putString("searchTextContent",searchText);
        searchResultFragment.setArguments(bundle);
        transaction.replace(R.id.id_linearlayout_main,searchResultFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @OnClick(R.id.search_button_search)
    public void search(View view){
        //获取输入框内容，并去掉多余的空格
        editTextContent = mEditText.getText().toString().trim();
        //判断搜索的内容是否为空格
        if (!editTextContent.equals("")) {
            //将搜索的内容写入历史记录
            mDoHistoryServices.writeHistory(editTextContent);
            //添加历史记录
            addViewAdapter(mDoHistoryServices.readHistory());
            //将将消息记录设置为可见
            mFlowLayout.setVisibility(View.VISIBLE);
            //将清除按钮设置为可见
            mButtonClear.setVisibility(View.VISIBLE);
//            //刷新显示历史记录的界面
            replaceNewFragment(editTextContent);
        }
        //当输入的内容为空格时直接打印提示信息
        else {
            Toast.makeText(getActivity(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
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
