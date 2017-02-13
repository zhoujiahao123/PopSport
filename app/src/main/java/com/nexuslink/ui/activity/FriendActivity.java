package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.app.BaseActivity;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.SearchInfo;
import com.nexuslink.model.friendmodel.FriendModelImpl;
import com.nexuslink.presenter.FriendPresenter;
import com.nexuslink.presenter.FriendPresenterImpl;
import com.nexuslink.ui.adapter.FriendFragmenPagerAdapter;
import com.nexuslink.ui.adapter.FriendRecyclerViewAdapter;
import com.nexuslink.ui.view.FriendView;
import com.nexuslink.util.IdUtil;
import com.nexuslink.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public class FriendActivity extends SwipeBackActivity implements FriendView,FriendRecyclerViewAdapter.CallbackListener{
    private static FriendActivity activity;
    private FriendFragmenPagerAdapter adapter;
    private FriendPresenter friendPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private FloatingSearchView searchView;
    public static FriendActivity getFriendActivity(){
        return activity;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Log.e("TAG","FriednActiivyt");
        activity = this;
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FriendRecyclerViewAdapter.setCallbackListener(this);
        showUserfragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void searchUser(int type,String uName) {
        friendPresenter.searchUser(type,uName);
        XLog.e("searchUser");
    }

    @Override
    public void showUserfragment() {
        Log.e("AG","showUserFragment");
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    public void succeedFollow() {

    }

    @Override
    public void failedFollow() {

    }

    @Override
    public void showSearchUser(SearchInfo searchInfo) {
        XLog.e(searchInfo.getUsers().get(0).getFId());
    }

    private void initView() {
        adapter = new FriendFragmenPagerAdapter(getSupportFragmentManager());
        Log.e(Constants.TAG,"initView");
        friendPresenter = new FriendPresenterImpl(new FriendModelImpl(),this);
        searchView = (FloatingSearchView) findViewById(R.id.group_search);
        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                XLog.e("点击搜索");
                if(item.getItemId()==R.id.search){
                    searchUser(1,searchView.getQuery());
                    searchView.clearQuery();
                    searchView.setSearchFocused(false);
                }
            }
        });
    }
    @Override
    public void onItemClicked(int uId,int fId) {
//        friendPresenter.followOne(uId, fId);
    }
}
