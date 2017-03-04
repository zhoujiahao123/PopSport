package com.nexuslink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexuslink.R;
import com.nexuslink.RunDao;
import com.nexuslink.ui.adapter.RunHistoryAdapter;
import com.nexuslink.util.DBUtil;
import com.nexuslink.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 猿人 on 2017/2/28.
 */

public class RunHistroyFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    //===============================================辅助变量
    private RunDao runDao = DBUtil.getRunDao();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.run_history_fragment, container, false);
        ButterKnife.bind(this, view);
        //设置RecyclerView
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        RunHistoryAdapter adapter = new RunHistoryAdapter(getContext(),runDao.loadAll());
        mRecycler.setAdapter(adapter);

        return view;
    }
}
