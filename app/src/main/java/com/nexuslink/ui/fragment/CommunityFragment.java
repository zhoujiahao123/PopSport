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
import com.nexuslink.ui.activity.WriteMsgActivity;
import com.nexuslink.ui.adapter.CommunityRecyclerAdapter;
import com.nexuslink.util.ToastUtil;

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
    //===============================================一数据
    private AppCompatActivity compatActivity;
    private CommunityRecyclerAdapter adapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compatActivity = (AppCompatActivity) getActivity();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_fragment, container, false);

        ButterKnife.bind(this, view);
        //首次进入刷新界面

        adapter = new CommunityRecyclerAdapter(getContext());
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置点击监听
        adapter.setUserIconClickListener(new CommunityRecyclerAdapter.userIconClickListener() {
            @Override
            public void onClickListener(int pos) {
                //根据位置进行获取用户数据（用户id）
                long userId = adapter.getUserId(pos);
                //网络请求道个人信息界面。。。。
                ToastUtil.showToast(getContext(),"点击用户头像");
            }
        });

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
