package com.nexuslink.ui.fragment;

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
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.ui.activity.WriteMsgActivity;
import com.nexuslink.ui.adapter.CommunityRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class CommunityFragment extends Fragment {
    //===============================================view
    @BindView(R.id.toolbar_community)
    Toolbar mToolbar;
    @BindView(R.id.recycler_community)
    RecyclerView mRecycler;
    //===============================================常量
    private static final String TAG = "CommunityFragment";
    private static final String IMAGE_URL = "http://img1.imgtn.bdimg.com/it/u=1794894692,1423685501&fm=21&gp=0.jpg";
    //===============================================一数据
    private List<CommunityInfo.CommunityBean> data = new ArrayList<>();
    private AppCompatActivity compatActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compatActivity = (AppCompatActivity) getActivity();
        initData();
    }

    private void initData() {
        for(int i =0;i<10;i++){
            CommunityInfo.CommunityBean bean = new CommunityInfo.CommunityBean();
            bean.setUserImageUrl(IMAGE_URL);
            bean.setUserName("张兴锐");
            bean.setUserLevel("19");
            bean.setContent("今天和一群傻逼打牌");
            String images[] = new String[11];
            for(int j=0;j<11;j++){
                images[j] = IMAGE_URL;
            }
            bean.setContentImagsUrl(images);
            data.add(bean);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_fragment, container, false);
        ButterKnife.bind(this, view);
        CommunityRecyclerAdapter adapter = new CommunityRecyclerAdapter(getContext(),data);
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        compatActivity.setSupportActionBar(mToolbar);
        compatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);


       setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.community_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.write_msg){
            Intent intent = new Intent(getActivity(), WriteMsgActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
