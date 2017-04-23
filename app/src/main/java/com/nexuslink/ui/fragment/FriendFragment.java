package com.nexuslink.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.nexuslink.R;
import com.nexuslink.model.FriendsInfo;
import com.nexuslink.model.data.FansInfo;
import com.nexuslink.presenter.personfriendpresenter.FriendPresenterImpl;
import com.nexuslink.ui.adapter.FansRecyclerAdapter;
import com.nexuslink.ui.view.ContainPresenterFragment;
import com.nexuslink.ui.view.IFansView;
import com.nexuslink.util.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 猿人 on 2017/4/9.
 */

public class FriendFragment extends ContainPresenterFragment implements IFansView<FriendsInfo.UsersBean>{

    @BindView(R.id.friends_recycler)
    EasyRecyclerView mRecyclerView;

    private FansRecyclerAdapter adapter;
    private FriendPresenterImpl presenter;

    @Override
    public void initPresenter() {
        presenter = new FriendPresenterImpl();
        presenter.onCreate();//创建model实例
        presenter.attachView(this);//注入view
        //请求数据
        presenter.getFriendInfo(UserUtils.getUserId());
    }

    @Override
    public int getLayout() {
        return R.layout.friends_list_fragment;
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setProgressView(R.layout.cube_ptr_simple_loading);
        mRecyclerView.setEmptyView(R.layout.empty_view);
        mRecyclerView.setErrorView(R.layout.peron_article_error);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4,GridLayoutManager.VERTICAL,false));

        adapter = new FansRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
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

    }

    @Override
    public void setDatas(List<FriendsInfo.UsersBean> datas) {
        List<FansInfo.FansBean> fans = new ArrayList<>();
        for(FriendsInfo.UsersBean usersBean: datas){
            FansInfo.FansBean fansBean = new FansInfo.FansBean();
            fansBean.setFId(usersBean.getFId());
            fansBean.setFImg(usersBean.getFImg());
            fansBean.setFAchievements(usersBean.getFAchievements());
            fansBean.setFName(usersBean.getFName());
            fans.add(fansBean);
        }
        adapter.addAll(fans);
    }
}
