package com.nexuslink.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nexuslink.R;
import com.nexuslink.model.data.RunHouseInfo;
import com.nexuslink.presenter.runhousepresenter.RunHousePresenter;
import com.nexuslink.ui.activity.CreateRunHouseActivity;
import com.nexuslink.ui.activity.RunHouseDetailActivity;
import com.nexuslink.ui.adapter.RunHouseAdapter;
import com.nexuslink.ui.view.RunHouseView;
import com.nexuslink.ui.view.view.headerview.LoadingView;
import com.nexuslink.ui.view.view.headerview.RunHouseFooter;
import com.nexuslink.ui.view.view.headerview.RunHouseHeader;
import com.nexuslink.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class AppointmentFragment extends Fragment implements RunHouseAdapter.OnClickListener, RunHouseView {

    //===============================================view
    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private List<RunHouseInfo.RunHouseBean> data = new ArrayList<>();
    private PtrFrameLayout ptrFrameLayout;
    private LoadingView proGress;
    //===============================================常量
    private static final String TAG = "AppointFramgment";
    //===============================================变量
    private Activity activity;
    private AppCompatActivity compatActivity;
    private  RunHouseAdapter adapter;
    private RunHousePresenter mRunHousePresenter;

    public static final String URL = "http://img0.imgtn.bdimg.com/it/u=2320677199,2423076609&fm=21&gp=0.jpg";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        compatActivity = (AppCompatActivity) activity;
        initData();
        mRunHousePresenter = new RunHousePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appointment_fragment,container,false);
        initView(view);
        adapter  = new RunHouseAdapter(getContext(),data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //点击接口
        adapter.setOnClickListener(this);

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
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                },1000);
            }
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                },1000);
            }
        });

        setHasOptionsMenu(true);
        return view;
    }


    private void initData() {
        for(int i =0;i<10;i++){
            RunHouseInfo.RunHouseBean runHouseBean = new RunHouseInfo.RunHouseBean();
            runHouseBean.setImageUrl(URL);
            runHouseBean.setName("房间"+i);
            runHouseBean.setCurrentPersons("5/20人");
            runHouseBean.setStartTime("22:22");
            runHouseBean.setRunType(i%2);
            runHouseBean.setRunDetail("de");
            data.add(runHouseBean);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.runhouse_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.create_house){
            //跳转
            Intent intent = new Intent(getActivity(), CreateRunHouseActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    /**
     * 点击事件处理
     * @param view
     * @param pos
     */
    @Override
    public void onItemClickListener(View view, int pos) {
        //取得数据
       long runHouseId = adapter.getRunHouseId(pos);
        //根据id进行请求
        mRunHousePresenter.loadDetail(runHouseId);

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
        ToastUtil.showToast(getContext(),"请求出错，请重试");;
    }

    @Override
    public void intentDetailActivity() {
        Intent intent = new Intent(activity, RunHouseDetailActivity.class);
        //跑房名，跑房人信息，头像，总公里数，跑房房主
        startActivity(intent);
    }
}
