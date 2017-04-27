package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.RankInfo;
import com.nexuslink.model.rankmodel.RankModelImpl;
import com.nexuslink.presenter.rankpresenter.RankPresenter;
import com.nexuslink.presenter.rankpresenter.RankPresenterImpl;
import com.nexuslink.ui.adapter.MyAdapter;
import com.nexuslink.ui.adapter.RankRecyclerViewAdapter;
import com.nexuslink.ui.view.RankView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.DividerItemDecoration;
import com.nexuslink.util.loader.LoaderFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ASUS-NB on 2017/2/13.
 */

public class RankActivity extends SwipeBackActivity implements RankView, RankRecyclerViewAdapter.OnItemClickListener {


    @BindView(R.id.head_second)
    CircleImageView headSecond;
    @BindView(R.id.nick_name_second)
    TextView nickNameSecond;
    @BindView(R.id.division)
    ImageView division;
    @BindView(R.id.run_num)
    ImageView runNum;
    @BindView(R.id.head_first)
    CircleImageView headFirst;
    @BindView(R.id.nick_name_first)
    TextView nickNameFirst;
    @BindView(R.id.division2)
    ImageView division2;
    @BindView(R.id.run_num_2)
    ImageView runNum2;
    @BindView(R.id.head_third)
    CircleImageView headThird;
    @BindView(R.id.nick_name_third)
    TextView nickNameThird;
    @BindView(R.id.division3)
    ImageView division3;
    @BindView(R.id.run_num3)
    ImageView runNum3;
    @BindView(R.id.top_bar)
    LinearLayout topBar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.text_num)
    TextView textNum;
    @BindView(R.id.text_num2)
    TextView textNum2;
    @BindView(R.id.text_num3)
    TextView textNum3;
    private MyAdapter adapter;
    private RankPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        presenter = new RankPresenterImpl(this, new RankModelImpl());
        presenter.getRankInfo();
        adapter = new MyAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void showRank(RankInfo rankInfo) {
        LoaderFactory.getGlideLoader().loadNet(headFirst, Constants.PHOTO_BASE_URL + rankInfo.getUsers().get(0).getUImg(), null);
        nickNameFirst.setText(rankInfo.getUsers().get(0).getUName());
        textNum2.setText(rankInfo.getUsers().get(0).getUHistoryMileage() + "米");

        LoaderFactory.getGlideLoader().loadNet(headSecond, Constants.PHOTO_BASE_URL + rankInfo.getUsers().get(1).getUImg(), null);
        nickNameSecond.setText(rankInfo.getUsers().get(1).getUName());
        textNum.setText(rankInfo.getUsers().get(1).getUHistoryMileage() + "米");

        LoaderFactory.getGlideLoader().loadNet(headThird, Constants.PHOTO_BASE_URL + rankInfo.getUsers().get(2).getUImg(), null);
        nickNameThird.setText(rankInfo.getUsers().get(2).getUName());
        textNum3.setText(rankInfo.getUsers().get(2).getUHistoryMileage() + "米");

        adapter.addData(rankInfo);
    }

    @Override
    public void onItemClock(int position) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }
}
