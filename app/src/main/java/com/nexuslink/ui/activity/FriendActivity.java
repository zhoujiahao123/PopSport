package com.nexuslink.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.data.SearchAndFollowedInfo;
import com.nexuslink.model.data.SearchInfo;
import com.nexuslink.model.friendmodel.FriendModelImpl;
import com.nexuslink.model.friendmodel.OnRegisterListener;
import com.nexuslink.model.friendmodel.OnSearchListener;
import com.nexuslink.presenter.friendpresenter.FriendPresenter;
import com.nexuslink.presenter.friendpresenter.FriendPresenterImpl;
import com.nexuslink.ui.adapter.FriendFragmenPagerAdapter;
import com.nexuslink.ui.adapter.FriendRecyclerViewAdapter;
import com.nexuslink.ui.view.FriendView;
import com.nexuslink.util.IdUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ASUS-NB on 2017/1/14.
 */

public class FriendActivity extends SwipeBackActivity implements FriendView, FriendRecyclerViewAdapter.CallbackListener {
    private static FriendActivity activity;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private FriendFragmenPagerAdapter adapter;
    private FriendPresenter friendPresenter;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private FloatingSearchView searchView;

    private SearchInfo mSearchInfo;
    int pos;

    public static FriendActivity getFriendActivity() {
        return activity;
    }
    static  OnRegisterListener mListener;
    static OnSearchListener searchListener;
    public static  void setOnRegisterListener(OnRegisterListener listener){

        mListener = listener;
    }
    public static void setOnSearchListener(OnSearchListener listener){
        searchListener = listener;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Log.e("TAG", "FriednActiivyt");
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
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void searchUser(int type, String uName) {
        friendPresenter.searchUser(type, uName);
        XLog.e("searchUser");
    }

    @Override
    public void showUserfragment() {
        Log.e("AG", "showUserFragment");

    }

    @Override
    public void startFollow(int uId, int fId) {

    }

    @Override
    public void succeedFollow() {
        mListener.onFollowSucceed(pos);
    }

    @Override
    public void failedFollow() {

    }

    @Override
    public void showSearchUser(SearchInfo searchInfo) {
        XLog.e(searchInfo.getUsers().get(0).getFId());
//        Intent intent = new Intent(this, FriendInfoActivity.class);
//        intent.putExtra("uId", searchInfo.getUsers().get(0).getFId());
//        intent.putExtra("uName", searchInfo.getUsers().get(0).getFName());
//        intent.putExtra("uImg", searchInfo.getUsers().get(0).getFImg());
//        startActivity(intent);
        XLog.e("showSearchUser执行了哦");
        mSearchInfo = searchInfo;
        getFollowed();
    }

    @Override
    public void getFollowed() {
        friendPresenter.getFriendInfo((int) IdUtil.getuId());
    }

    @Override
    public void getFollowedSucceed(FollowedInfo followedInfo) {
        if(mSearchInfo!=null){
            mListener.onRegister();
            searchListener.onSearched();
            SearchAndFollowedInfo info = new SearchAndFollowedInfo();
            info.setFollowedInfo(followedInfo);
            info.setSearchInfo(mSearchInfo);
            EventBus.getDefault().post(info);
        }else {
//            EventBus.getDefault().postSticky(followedInfo);
        }
    }

    private void initView() {
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

        tabLayout.setBackgroundColor(getResources().getColor(R.color.white_light));
        tabLayout.setTabTextColors(getResources().getColor(R.color.yellow_normal_light), getResources().getColor(R.color.yellow_normal_light));
        Log.e(Constants.TAG, "initView");
        friendPresenter = new FriendPresenterImpl(new FriendModelImpl(), this);
        searchView = (FloatingSearchView) findViewById(R.id.group_search);
        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                XLog.e("点击搜索");
                if (item.getItemId() == R.id.search) {
                    searchUser(1, searchView.getQuery());
                    searchView.clearQuery();
                    searchView.setSearchFocused(false);
                }
            }
        });
        adapter = new FriendFragmenPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        searchUser(1,"");
    }

    @Override
    public void onItemClicked(int uId, int fId,int pos) {
        friendPresenter.followOne(uId, fId);
        this.pos = pos;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(mListener!=null){
//            mListener.onUnRegister();
//        }
    }
}
