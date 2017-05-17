package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nexuslink.R;
import com.nexuslink.ui.adapter.FriendsAndFansViewPagerAdapter;
import com.nexuslink.ui.fragment.FansFragment;
import com.nexuslink.ui.fragment.FriendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendsAndFansActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tab_layout)
    TabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_and_fans);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        //设置toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("关注/粉丝");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.ghost_white));

        Fragment friendsFragment = new FriendFragment();
        Fragment fansFragment = new FansFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(friendsFragment);
        fragments.add(fansFragment);

        FriendsAndFansViewPagerAdapter adapter = new FriendsAndFansViewPagerAdapter(getSupportFragmentManager());
        adapter.setFragments(fragments);

        mViewPager.setAdapter(adapter);
        mTab.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
