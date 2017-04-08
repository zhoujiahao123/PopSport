package com.nexuslink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.app.BaseFragment;
import com.nexuslink.model.data.FansInfo;
import com.nexuslink.model.fansmodel.FansModelImpl;
import com.nexuslink.presenter.fanspresenter.FansPresenter;
import com.nexuslink.presenter.fanspresenter.FansPresenterImpl;
import com.nexuslink.ui.adapter.FansAdapter;
import com.nexuslink.ui.view.FansView;
import com.nexuslink.util.IdUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS-NB on 2017/2/24.
 */

public class FansFragment extends BaseFragment implements FansView{


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private FansPresenter presenter;
    FansAdapter adapter;


    public static FansFragment newInstance() {
        FansFragment fansFragment = new FansFragment();
        return fansFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgament_myfans, null);
        ButterKnife.bind(this, view);
        presenter = new FansPresenterImpl(new FansModelImpl(),this);
        presenter.getFans((int) IdUtil.getuId());
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        XLog.e("这里调用  能看见");
        getFans((int) IdUtil.getuId());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void load(int uId){

    }

    @Override
    public void getFans(int uId) {
        load(uId);
    }

    @Override
    public void setFans(FansInfo fans) {
        adapter = new FansAdapter(getContext(),fans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }
}
