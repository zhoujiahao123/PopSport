package com.nexuslink.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nexuslink.R;
import com.nexuslink.model.data.LoadRoomsResult;
import com.nexuslink.presenter.runhousepresenter.RunHousePresenter;
import com.nexuslink.presenter.runhousepresenter.RunHousePresenterImpl;
import com.nexuslink.ui.activity.CreateRunHouseActivity;
import com.nexuslink.ui.adapter.RunHouseAdapter;
import com.nexuslink.ui.view.RunHouseView;
import com.nexuslink.ui.view.view.headerview.LoadingView;
import com.nexuslink.ui.view.view.headerview.RunHouseFooter;
import com.nexuslink.ui.view.view.headerview.RunHouseHeader;
import com.nexuslink.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class AppointmentFragment extends Fragment implements RunHouseView {

    //===============================================view
    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private List<LoadRoomsResult.RoomBean> data = new ArrayList<>();
    private PtrFrameLayout ptrFrameLayout;
    private LoadingView proGress;
    private FloatingActionButton fab;

    //===============================================常量
    private static final String TAG = "AppointFramgment";
    //===============================================变量
    private Activity activity;
    private AppCompatActivity compatActivity;
    private RunHouseAdapter adapter;
    private RunHousePresenter mRunHousePresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        compatActivity = (AppCompatActivity) activity;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appointment_fragment, container, false);
        initView(view);

        mRunHousePresenter = new RunHousePresenterImpl(this);
        //首次进入时进行刷新
        mRunHousePresenter.onRefresh(0, true);

        adapter = new RunHouseAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //设置下拉刷新
        //下拉刷新和上拉加载更多
        ptrFrameLayout.setMode(PtrFrameLayout.Mode.BOTH);
        //header
        RunHouseHeader runHouseHeader = new RunHouseHeader(getContext());
        ptrFrameLayout.setHeaderView(runHouseHeader);
        ptrFrameLayout.addPtrUIHandler(runHouseHeader);
        //footer
        RunHouseFooter runHouseFooter = new RunHouseFooter(getContext());
        ptrFrameLayout.setFooterView(runHouseFooter);
        ptrFrameLayout.addPtrUIHandler(runHouseFooter);
        //监听事件
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mRunHousePresenter.onRefresh(0, false);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                int index = adapter.getDatas().size();
                if (index > 0) {
                    int id = adapter.getDatas().get(index - 1).getRoomId() + 1;
                    mRunHousePresenter.onLoadMore(adapter.getDatas().get(index - 1).getRoomId());
                } else {
                    ToastUtil.showToast(getContext(), "加载时出错，请重试");
                }
            }
        });

        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        return view;
    }


    @Subscribe
    public void onRefresh(String str) {
        if (str.equals("刷新跑房")) {
            mRunHousePresenter.onRefresh(0, false);
        }
    }

    private void initView(View view) {

        proGress = (LoadingView) view.findViewById(R.id.progress_appoint);

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_appoint);
        compatActivity.setSupportActionBar(mToolbar);
        compatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        ptrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptrFrame);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_appoint);


    }


    @Override
    public void showProgress() {
        proGress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        proGress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError() {
        ToastUtil.showToast(getContext(), "请求出错，请重试");
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void showSuccess() {
        ToastUtil.showToast(getContext(), "刷新成功");
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void showNoMore() {
        ToastUtil.showToast(getContext(), "没有更多了");
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void setRunHouseDatas(List<LoadRoomsResult.RoomBean> list) {
        adapter.setDatas(list);
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void addRunHouse(List<LoadRoomsResult.RoomBean> list) {
        adapter.addItems(list);
        ptrFrameLayout.refreshComplete();
    }

    @OnClick(R.id.fab)
    public void onClick() {
        //跳转
        Intent intent = new Intent(getActivity(), CreateRunHouseActivity.class);
        startActivity(intent);
    }
}
