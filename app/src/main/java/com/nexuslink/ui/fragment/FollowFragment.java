package com.nexuslink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.nexuslink.R;
import com.nexuslink.app.BaseFragment;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.ui.adapter.FollowAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS-NB on 2017/2/24.
 */

public class FollowFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.container)
    RelativeLayout container;
    private FollowedInfo mFollowedInfo;

    FollowAdapter adapter;


    public static FollowFragment getInstance() {
        FollowFragment followFragment = new FollowFragment();
        return followFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, container, false);
        ButterKnife.bind(this, view);
        adapter = new FollowAdapter(getContext(), mFollowedInfo);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
