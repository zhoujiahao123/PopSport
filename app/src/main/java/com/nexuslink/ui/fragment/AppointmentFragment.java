package com.nexuslink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.nexuslink.R;
import com.nexuslink.model.data.RunHouseInfo;
import com.nexuslink.ui.adapter.RunHouseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class AppointmentFragment extends Fragment {
    private Toolbar mToolbar;
    private PtrFrameLayout ptrFrameLayout;
    private RecyclerView recyclerView;
    private Random random = new Random();
    private List<RunHouseInfo.RunHouseBean> data = new ArrayList<>();

    public static final String URL = "http://img0.imgtn.bdimg.com/it/u=2320677199,2423076609&fm=21&gp=0.jpg";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appointment_fragment,container,false);
        initView(view);
        initData();
        RunHouseAdapter adapter = new RunHouseAdapter(getContext(),data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
//        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_appoint);
        ptrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr_appoint);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_appoint);
    }
}
