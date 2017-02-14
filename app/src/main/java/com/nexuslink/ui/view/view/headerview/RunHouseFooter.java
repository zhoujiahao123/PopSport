package com.nexuslink.ui.view.view.headerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.nexuslink.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by 猿人 on 2017/2/3.
 */

public class RunHouseFooter extends FrameLayout implements PtrUIHandler {
    public RunHouseFooter(Context context) {
        this(context,null);
    }

    public RunHouseFooter(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RunHouseFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.ruuhouse_footer,this);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
