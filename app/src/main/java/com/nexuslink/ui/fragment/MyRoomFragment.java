package com.nexuslink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.nexuslink.R;
import com.nexuslink.model.data.RoomsBean;
import com.nexuslink.presenter.runhousepresenter.RunHousePresenterImpl;
import com.nexuslink.ui.adapter.RunHouseAdapter;
import com.nexuslink.ui.view.ContainPresenterFragment;
import com.nexuslink.ui.view.RunHouseView;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 猿人 on 2017/4/9.
 */

public class MyRoomFragment extends ContainPresenterFragment implements RunHouseView<RoomsBean>{

    @BindView(R.id.room_list_recycler)
    EasyRecyclerView mRecyclerView;

    private RunHouseAdapter adapter;
    private RunHousePresenterImpl presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getLayout() {
        return R.layout.person_room_list_fragment;
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setProgressView(R.layout.progress_view);
        mRecyclerView.setEmptyView(R.layout.no_rooms_view);
        mRecyclerView.setErrorView(R.layout.peron_article_error);
        adapter = new RunHouseAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initPresenter() {
        presenter = new RunHousePresenterImpl();
        presenter.onCreate();
        presenter.attachView(this);
        //请求
        presenter.getMyRooms(UserUtils.getUserId());
    }

    @Override
    public void showMsg(String message) {
        ToastUtil.showToast(getContext(),message);
    }

    @Override
    public void showProgress() {
        mRecyclerView.showProgress();
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showError(String message) {
        mRecyclerView.showError();
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showNoMore() {

    }

    @Override
    public void setRunHouseDatas(List<RoomsBean> list) {
        adapter.setDatas(list);
    }

    @Override
    public void addRunHouse(List<RoomsBean> list) {
        adapter.addItems(list);
    }

    @Subscribe
    public void onRefresh(String str) {
        if (str.equals("刷新跑房")) {
            presenter.getMyRooms(UserUtils.getUserId());
        }
    }
}
