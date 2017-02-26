package com.nexuslink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.app.BaseFragment;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.myfriendmodel.MyFriendModelImpl;
import com.nexuslink.presenter.myfriendpresenter.MyFriendPresenter;
import com.nexuslink.presenter.myfriendpresenter.MyFriendPresenterImpl;
import com.nexuslink.ui.adapter.MyFriendRecyclerViewAdapter;
import com.nexuslink.ui.view.MyFriendView;
import com.nexuslink.util.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class MyFriendFragment extends BaseFragment implements MyFriendView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private MyFriendPresenter presenter;
    private boolean isCreate=false;
    private MyFriendRecyclerViewAdapter adapter;



    public static MyFriendFragment getInstance() {
        MyFriendFragment friendFragment = new MyFriendFragment();
        return friendFragment;
    }
    private void load(){
        isCreate=false;
        presenter=new MyFriendPresenterImpl(new MyFriendModelImpl(),this);
        presenter.setFriendInfo();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreate=true;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isCreate&&isVisibleToUser){
            load();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myfriend, container, false);
        ButterKnife.bind(this, view);
        isCreate = true;
        return view;
    }

    @Override
    public void sendMessage() {

    }

    @Override
    public void showFriendInfo() {

    }

    @Override
    public void showFriend(FollowedInfo followedInfo) {
        XLog.e("showFriend    aaaaa" );
        adapter = new MyFriendRecyclerViewAdapter(getContext(),followedInfo);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL_LIST));
        recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void showFriendError() {

    }

}
