package com.example.stevengo.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.stevengo.myapplication.R;


/**
 * Created by StevenGo on 2017/9/26.
 * 自定义ListView，实现下拉刷新,加载更多
 */

public class SearchResultListView extends ListView implements OnScrollListener {
    //头部下拉刷新的布局
    private View header;
    //尾部加载更多的布局
    private View footer;
    //头部的高度
    private int headerHeight;
    //当前第一个可见的Item的位置
    private int firstVisibleItem;
    private int totalItemCount;
    private int lastVisibleItem;
    //Listview的滚动状态
    private int scrollState;
    //是否是在ListView最顶端摁下的
    boolean isRemark;
    //摁下时Y的值
    int startY;
    IRefreshListener iRefreshListener;
    ILoadListener iLoadListener;
    private boolean isLoading;


    // 当前的状态；
    int state;
    // 正常状态；
    final int NONE = 0;
    // 提示下拉状态；
    final int PULL = 1;
    // 提示释放状态；
    final int RELESE = 2;
    // 刷新状态；
    final int REFLASHING = 3;

    //滑动方向
    int slideDirection;
    final int UP = 0;
    final int DOWN = 1;

    /**
     * 重写一个参数的构造方法，加入初始化视图的功能
     */
    public SearchResultListView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * 重写两个参数的构造方法，加入初始化视图的功能
     */
    public SearchResultListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 重写三个参数的构造方法，加入初始化视图的功能
     */
    public SearchResultListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化视图
     */
    private void initView(Context context) {
        //通过反射加载布局
        header = LayoutInflater.from(context).inflate(R.layout.search_result_listview_header, null);
        footer = LayoutInflater.from(context).inflate(R.layout.search_result_listview_footer, null);
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);

        //将布局添加到ListView
        this.addHeaderView(header);
        this.addFooterView(footer);

        //通知父布局header的高度
        measureView(header);
        //获取header的高度
        headerHeight = header.getMeasuredHeight();
        Log.d("StevenGo", "initView: " + headerHeight);
        //将topPadding设置为高度的负值
        this.setTopPadding(-headerHeight);
        setOnScrollListener(this);
    }

    /**
     * 通知父布局该组件的高度和宽度
     */
    private void measureView(View view) {
        //获取组件的LayoutParams;
        ViewGroup.LayoutParams p = view.getLayoutParams();

        //当LayoutParams不存在的时候创建一个
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //获取宽度
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        //获取高度
        int tempHeight = p.height;
        //判断其高度是否为0
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight,
                    MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        //通知父控件自己的宽度和高度
        view.measure(width, height);
    }

    /**
     * 设置topPadding的方法
     */
    private void setTopPadding(int topPadding) {
        //设置header的Padding
        header.setPadding(header.getPaddingLeft(), topPadding,
                header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    /**
     * 获取当前第一个可见的item的位置
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;
    }

    /**
     * 获取滚动状态
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        if (lastVisibleItem == totalItemCount && scrollState == SCROLL_STATE_IDLE && slideDirection == UP) {
            if (!isLoading) {
                isLoading = true;
                footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                iLoadListener.onLoad();
            }
        }
    }

    /**
     * 重写onTouchEvent响应时间，加入对header
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            //摁下时的操作
            case MotionEvent.ACTION_DOWN:
                //如果当前可见位置在最前面
                if (firstVisibleItem == 0) {
                    //标志设置为true
                    isRemark = true;
                    //记录当前的高度
                    startY = (int) ev.getY();
                }
                break;
            //移动时的操作
            case MotionEvent.ACTION_MOVE:
                //调用移动时的处理方法
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if (state == RELESE) {
                    state = REFLASHING;
                    // 加载最新数据；
                    reflashViewByState();
                    iRefreshListener.onRefresh();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    //移动时的处理方法
    private void onMove(MotionEvent ev) {
        //当不是在最顶端摁下时返回
        if (!isRemark) {
            return;
        }
        //移动到的位置
        int tempY = (int) ev.getY();
        //移动的距离
        int space = tempY - startY;
        //设置顶端的距离
        int topPadding = space - headerHeight;
        //防止list无限制的下拉
        if (topPadding > headerHeight + 50) {
            topPadding = headerHeight + 50;
        }
        //判断滑动的方向，判断当内容填充不满时的操作
        if (space < 0) {
            slideDirection = UP;

        } else {
            slideDirection = DOWN;
        }

        //判断当前的状态
        switch (state) {
            //正常
            case NONE:
                //如果距离变成正值，将状态设置为下拉,更新视图
                if (space > 0) {
                    state = PULL;
                    reflashViewByState();
                }
                break;
            //拉动
            case PULL:
                //设置topPadding的值
                setTopPadding(topPadding);
                //当距离大于高度的值+30并且在滚动时，设置为RELESE状态,更新视图
                if (space > headerHeight + 30
                        && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELESE;
                    reflashViewByState();
                }
                break;

            //释放状态
            case RELESE:
                //设置topPadding的值
                setTopPadding(topPadding);
                //移动距离小于临界值
                if (space < headerHeight + 30) {
                    state = PULL;
                    reflashViewByState();
                }
                ////没有移动时
                else if (space <= 0) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;
        }
    }

    /**
     * 根据当前状态，改变界面显示；
     */
    private void reflashViewByState() {
        //根据id获取组件
        TextView tip = (TextView) header.findViewById(R.id.tip);
        ImageView refreshIcon = (ImageView) header.findViewById(R.id.refresh_icon);
        ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);

        //创建动画
        RotateAnimation animPull = new RotateAnimation(0, 180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        //设置动画执行的时间
        animPull.setDuration(500);
        //使能该效果
        animPull.setFillAfter(true);

        //松开时的动画
        RotateAnimation animRelese = new RotateAnimation(180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animRelese.setDuration(500);
        animRelese.setFillAfter(true);

        switch (state) {
            case NONE:
                //清除动画
                refreshIcon.clearAnimation();
                setTopPadding(-headerHeight);
                break;
            case PULL:
                refreshIcon.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText(R.string.pull_for_refresh);
                refreshIcon.clearAnimation();
                refreshIcon.setAnimation(animRelese);
                break;
            case RELESE:
                refreshIcon.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText(R.string.relese_for_refresh);
                refreshIcon.clearAnimation();
                refreshIcon.setAnimation(animPull);
                break;
            case REFLASHING:
                setTopPadding(50);
                refreshIcon.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tip.setText(R.string.refreshing);
                refreshIcon.clearAnimation();
                break;
        }
    }

    /**
     * 刷新结束后的操作
     */
    public void refreshComplete() {
        state = NONE;
        isRemark = false;
        reflashViewByState();
    }

    public void loadComplete() {
        isLoading = false;
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
    }

    /**
     * 刷新数据接口
     */
    public interface IRefreshListener {
        void onRefresh();
    }

    public void setInterfaceRefresh(IRefreshListener iRefreshListener) {
        this.iRefreshListener = iRefreshListener;
    }

    public interface ILoadListener {
        void onLoad();
    }

    public void setInterfaceLoad(ILoadListener iLoadListener) {
        this.iLoadListener = iLoadListener;
    }

}
