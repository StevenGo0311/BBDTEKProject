package com.example.stevengo.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by StevenGo on 2017/9/18.
 * 自定义ViewGroup,创建流式布局
 */

public class FlowLayout extends ViewGroup {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 有内容的宽度
     */
    private int usefulWidth;
    /**
     * 行与行之间的距离
     */
    private int lineSpacing = 0;
    /**
     * 容器的子控件
     */
    List<View> childList = new ArrayList();

    List<Integer> lineNumList = new ArrayList();

    /**
     * 重写构造方法
     */
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    /**
     * 计算所有子组件的宽度和高度，然后根据计算结果设置自己的宽度和高度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取上，下，左，右的Padding值
        int mPaddingLeft = getPaddingLeft();
        int mPaddingRight = getPaddingRight();
        int mPaddingTop = getPaddingTop();
        int mPaddingBottom = getPaddingBottom();

        //获取其上级容器为其建议的宽度高度和计算模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int lineUsed = mPaddingLeft + mPaddingRight;
        //lineY初始化为上边的Padding
        int lineY = mPaddingTop;
        //行高初始化为0
        int lineHeight = 0;

        //遍历其所有的子控件
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            //当子控件不可见时跳过
            if (child.getVisibility() == GONE) {
                continue;
            }
            int spaceWidth = 0;
            int spaceHeight = 0;
            LayoutParams childLp = child.getLayoutParams();
            //当childLP是MarginLayoutParams的对象时，测量其MarginLayoutParams的大小
            if (childLp instanceof MarginLayoutParams) {
                //测量大小
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, lineY);
                //将LayoutParams的对象转换成MarginLayoutParams的对象
                MarginLayoutParams mlp = (MarginLayoutParams) childLp;
                //获取其上下左右的Margin
                spaceWidth = mlp.leftMargin + mlp.rightMargin;
                spaceHeight = mlp.topMargin + mlp.bottomMargin;
            }
            //当不是的时候直接测量
            else {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
            //测量子控件的宽度和高度
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            spaceWidth += childWidth;
            spaceHeight += childHeight;
            //当控件的宽度大于整体宽度时跳到下一行
            if (lineUsed + spaceWidth > widthSize) {

                lineY += lineHeight + lineSpacing;
                lineUsed = mPaddingLeft + mPaddingRight;
                lineHeight = 0;
            }
            if (spaceHeight > lineHeight) {
                lineHeight = spaceHeight;
            }
            lineUsed += spaceWidth;
        }
        //存储得到的宽度和高度
        setMeasuredDimension(
                widthSize,
                heightMode == MeasureSpec.EXACTLY ? heightSize : lineY + lineHeight + mPaddingBottom
        );
    }

    /**
     * 对所有的child进行定位
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //获取左边，右边，上边的Pagdding值
        int mPaddingLeft = getPaddingLeft();
        int mPaddingRight = getPaddingRight();
        int mPaddingTop = getPaddingTop();
        //将lineX设置为左边的Padding
        int lineX = mPaddingLeft;
        //将lineY设置为上边的padding
        int lineY = mPaddingTop;
        int lineWidth = r - l;
        //减去多余的长度
        usefulWidth = lineWidth - mPaddingLeft - mPaddingRight;
        int lineUsed = mPaddingLeft + mPaddingRight;
        int lineHeight = 0;
        int lineNum = 0;

        lineNumList.clear();
        //将所有的子控件添加到容器
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            //容器不可见时跳过
            if (child.getVisibility() == GONE) {
                continue;
            }
            //定义整型变量分别表示空格，上下左右距离，子控件的大小
            int spaceWidth = 0;
            int spaceHeight = 0;
            int left = 0;
            int top = 0;
            int right = 0;
            int bottom = 0;
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            LayoutParams childLp = child.getLayoutParams();

            //判断子控件是否是MarginLayoutParams的对象
            if (childLp instanceof MarginLayoutParams) {
                MarginLayoutParams mlp = (MarginLayoutParams) childLp;
                //获取其上下左右空格的大小
                spaceWidth = mlp.leftMargin + mlp.rightMargin;
                spaceHeight = mlp.topMargin + mlp.bottomMargin;
                //左边
                left = lineX + mlp.leftMargin;
                //顶部
                top = lineY + mlp.topMargin;
                //右边
                right = lineX + mlp.leftMargin + childWidth;
                //底部
                bottom = lineY + mlp.topMargin + childHeight;
            } else {
                left = lineX;
                top = lineY;
                right = lineX + childWidth;
                bottom = lineY + childHeight;
            }

            spaceWidth += childWidth;
            spaceHeight += childHeight;

            //当同一行不能放置下一个控件的时候跳到下一行
            if (lineUsed + spaceWidth > lineWidth) {
                lineNumList.add(lineNum);
                lineY += lineHeight + lineSpacing;
                lineUsed = mPaddingLeft + mPaddingRight;
                lineX = mPaddingLeft;
                lineHeight = 0;
                lineNum = 0;
                if (childLp instanceof MarginLayoutParams) {
                    MarginLayoutParams mlp = (MarginLayoutParams) childLp;
                    left = lineX + mlp.leftMargin;
                    top = lineY + mlp.topMargin;
                    right = lineX + mlp.leftMargin + childWidth;
                    bottom = lineY + mlp.topMargin + childHeight;
                } else {
                    left = lineX;
                    top = lineY;
                    right = lineX + childWidth;
                    bottom = lineY + childHeight;
                }
            }

            child.layout(left, top, right, bottom);
            lineNum++;

            if (spaceHeight > lineHeight) {
                lineHeight = spaceHeight;
            }
            lineUsed += spaceWidth;
            lineX += spaceWidth;
        }
        lineNumList.add(lineNum);
    }

    /**
     * 重写generateLayoutParams，确定该ViewGroup的LayoutParams返回MarginLayoutParams的实例
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
