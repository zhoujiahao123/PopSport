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
import com.nexuslink.util.ToastUtil;

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
    private static final String IMAGE_URL[] = {"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1598552568,4236159349&fm=58",
    "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=164888333,246711798&fm=116&gp=0.jpg",
    "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4271053251,2424464488&fm=116&gp=0.jpg",
    "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2320677199,2423076609&fm=116&gp=0.jpg",
    "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=819201812,3553302270&fm=116&gp=0.jpg",
    "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1303680113,133301350&fm=116&gp=0.jpg",
    "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1799345195,2075280808&fm=116&gp=0.jpg"};
    //===============================================一数据
    private List<CommunityInfo.CommunityBean> data = new ArrayList<>();
    private AppCompatActivity compatActivity;
    private CommunityRecyclerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compatActivity = (AppCompatActivity) getActivity();
        initData();
    }

    private void initData() {
        for(int i =0;i<10;i++){
            CommunityInfo.CommunityBean bean = new CommunityInfo.CommunityBean();
            bean.setUserImageUrl(IMAGE_URL[i%IMAGE_URL.length]);
            bean.setUserName("张兴锐");
            bean.setUserLevel(19);
            bean.setText("今天和一群傻逼打牌");
            List<String> images = new ArrayList<>();
            for(int j=0;j<11;j++){
                images.add(IMAGE_URL[j%IMAGE_URL.length]);
            }
            bean.setImages(images);
            data.add(bean);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_fragment, container, false);
        ButterKnife.bind(this, view);
        adapter = new CommunityRecyclerAdapter(getContext(),data);
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
