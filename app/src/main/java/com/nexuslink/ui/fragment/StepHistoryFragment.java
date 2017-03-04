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
import com.nexuslink.StepsDao;
import com.nexuslink.ui.adapter.StepHistoryAdapter;
import com.nexuslink.util.DBUtil;
import com.nexuslink.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 猿人 on 2017/2/28.
 */

public class StepHistoryFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    //===============================================辅助变量
    private StepsDao stepsDao = DBUtil.getStepsDao();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_history_fragment, container, false);
        ButterKnife.bind(this, view);
        //recyclerview设置
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        mRecycler.setAdapter(new StepHistoryAdapter(getContext(), stepsDao.loadAll()));
        return view;
    }
}
