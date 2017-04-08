package com.nexuslink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.nexuslink.R;
import com.nexuslink.ui.adapter.FriendRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 猿人 on 2017/4/8.
 */

public class FriendsFragment extends Fragment {
    /**
     * -
     * view
     */
    @BindView(R.id.friends_recycler)
    EasyRecyclerView mRecyclerView;
    Unbinder unbinder;

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FriendRecyclerAdapter adapter = new FriendRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
        /**
         * 剩下网络请求
         */
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
