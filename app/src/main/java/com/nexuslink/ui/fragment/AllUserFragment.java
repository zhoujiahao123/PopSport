package com.nexuslink.ui.fragment;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexuslink.R;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.app.BaseFragment;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.SearchInfo;
import com.nexuslink.ui.adapter.FriendFragmenPagerAdapter;
import com.nexuslink.ui.adapter.FriendRecyclerViewAdapter;
import com.nexuslink.ui.view.AllUserView;
import com.nexuslink.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public class AllUserFragment extends BaseFragment implements AllUserView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private FriendRecyclerViewAdapter adapter;
    SearchInfo searchInfo;

    public static AllUserFragment getInstance(SearchInfo searchInfo){
        AllUserFragment fragment = new AllUserFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alluser, container, false);
        ButterKnife.bind(this, view);
        Log.e(Constants.TAG,"AllUserFragment");
        adapter = new FriendRecyclerViewAdapter(getActivity(),searchInfo);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL_LIST));
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void showFollow() {

    }

    @Override
    public void showUserInfo() {

    }
}
