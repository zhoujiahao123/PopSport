package com.nexuslink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.nexuslink.R;
import com.nexuslink.model.data.FansInfo;
import com.nexuslink.presenter.personfriendpresenter.FriendPresenterImpl;
import com.nexuslink.ui.adapter.FansRecyclerAdapter;
import com.nexuslink.ui.view.IFansView;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 猿人 on 2017/4/8.
 */

public class FriendsFragment extends Fragment implements IFansView<FansInfo.FansBean> {
    /**
     * -
     * view
     */
    @BindView(R.id.friends_recycler)
    EasyRecyclerView mRecyclerView;
    Unbinder unbinder;

    /**
     * 数据
     */
    private FansRecyclerAdapter adapter;
    private FriendPresenterImpl presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FriendPresenterImpl();
        presenter.onCreate();
        presenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mRecyclerView.setEmptyView(R.layout.empty_view);
        mRecyclerView.setErrorView(R.layout.peron_article_error);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4,GridLayoutManager.VERTICAL,false));

        adapter = new FansRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
        /**
         * 剩下网络请求
         */
        presenter.getFansInfo(UserUtils.getUserId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showProgress() {
        mRecyclerView.showProgress();
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {
        ToastUtil.showToast(getContext(), message);
    }

    @Override
    public void setDatas(List<FansInfo.FansBean> datas) {
        adapter.addAll(datas);
    }
}
