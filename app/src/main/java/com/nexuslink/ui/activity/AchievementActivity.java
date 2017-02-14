package com.nexuslink.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toolbar;

import com.nexuslink.R;
import com.nexuslink.ui.adapter.AchievementRecyclerViewAdapter;
import com.nexuslink.util.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ASUS-NB on 2017/2/13.
 */

public class AchievementActivity extends SwipeBackActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private AchievementRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        adapter = new AchievementRecyclerViewAdapter(this);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL_LIST));
        recyclerview.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.turn_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
        getActionBar().setDisplayShowTitleEnabled(false);
    }
}
