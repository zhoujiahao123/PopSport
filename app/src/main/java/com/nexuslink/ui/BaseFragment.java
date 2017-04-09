package com.nexuslink.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 猿人 on 2017/4/9.
 */

public abstract  class BaseFragment extends Fragment {
    /**
     * 取得layoutid
     */
    public abstract int getLayout();
    /**
     * 初始化view
     */
    public abstract void initView();
    protected Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(),container,false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
