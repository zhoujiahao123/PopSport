package com.nexuslink.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

/**
 * Created by ASUS-NB on 2017/2/2.
 */

public class MyScrollView extends ScrollView{
    private int num=100;
    public interface OnScrollListener{
        void onChangeBackground(float scaleRadio);
    }
    private static  OnScrollListener mOnScrollListener;

    public static void setScrollListener(OnScrollListener onScrollListener){
        mOnScrollListener= onScrollListener;
    }
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.e("TAG t",t+"");
        if(Math.abs(t-num)>200){
            mOnScrollListener.onChangeBackground((t)/3);
            if(t-num>0){
                num+=200;
            }else if(t-num<0){
                num-=200;
            }
        }
    }
}
