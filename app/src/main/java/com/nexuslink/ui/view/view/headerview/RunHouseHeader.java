package com.nexuslink.ui.view.view.headerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nexuslink.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by 猿人 on 2017/2/3.
 */

public class RunHouseHeader extends FrameLayout implements PtrUIHandler {
    private TextView textView;
    public RunHouseHeader(Context context) {
        this(context,null);
    }

    public RunHouseHeader(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RunHouseHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.runhouse_header,this);
        textView = (TextView) findViewById(R.id.run_house_text);
    }
    public void refreshComplete(){
        textView.setText("completed");
    }
    public void startLoading(){
        textView.setText("loading...");
    }
    public void prepareLoading(){
        textView.setText("waiting...");
    }
    @Override
    public void onUIReset(PtrFrameLayout frame) {
        startLoading();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        prepareLoading();
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        startLoading();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {
        if(isHeader){
            refreshComplete();
        }

    }


    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
