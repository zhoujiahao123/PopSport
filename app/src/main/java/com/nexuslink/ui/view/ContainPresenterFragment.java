package com.nexuslink.ui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexuslink.ui.fragment.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by 猿人 on 2017/4/9.
 */

public abstract class ContainPresenterFragment extends BaseFragment {
    /**
     * 初始化presenter
     */
    public abstract void initPresenter();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initPresenter();
        return view;
    }


}
