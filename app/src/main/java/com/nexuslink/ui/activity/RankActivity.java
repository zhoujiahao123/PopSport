package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nexuslink.R;
import com.nexuslink.ui.adapter.RankRecyclerViewAdapter;
import com.nexuslink.ui.view.RankView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ASUS-NB on 2017/2/13.
 */

public class RankActivity extends SwipeBackActivity implements RankView {

    @BindView(R.id.head_image)
    CircleImageView headImage;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private RankRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        adapter = new RankRecyclerViewAdapter(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void showRank() {

    }
}
