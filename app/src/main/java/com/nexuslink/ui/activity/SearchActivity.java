package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.SearchInfo;
import com.nexuslink.model.search.RandomSearchResult;
import com.nexuslink.ui.adapter.RandomFriendsAdapter;
import com.nexuslink.ui.adapter.SearchAdapter;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_input)
    EditText searchInput;
    @BindView(R.id.search_result)
    EasyRecyclerView mRecycler;

    private SearchAdapter adapter;
    private RandomFriendsAdapter randomFriendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">"+"搜索"+"</font>"));


        adapter = new SearchAdapter(this);
        randomFriendsAdapter = new RandomFriendsAdapter(this);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(adapter);

    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.random,menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.random:
                ApiUtil.getInstance(Constants.BASE_URL).getRandowmUsers(UserUtils.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RandomSearchResult>() {
                    @Override
                    public void call(RandomSearchResult randomSearchResult) {
                        if(randomSearchResult.getCode() == Constants.SUCCESS){

                            if(randomFriendsAdapter.getCount() != 0){
                                randomFriendsAdapter.clear();
                            }
                            mRecycler.setAdapter(randomFriendsAdapter);
                            randomFriendsAdapter.addAll(randomSearchResult.getUsers());
                        }
                    }

                });

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.confirm_tv)
    public void onViewClicked() {
        ApiUtil.getInstance(Constants.BASE_URL).searchUser(1, searchInput.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SearchInfo>() {
                    @Override
                    public void call(SearchInfo searchInfo) {
                        if (searchInfo.getCode() == Constants.SUCCESS) {
                            if(adapter.getCount() != 0){
                                adapter.clear();
                            }
                            mRecycler.setAdapter(adapter);
                            if(searchInfo.getUsers().size() == 0){
                                ToastUtil.showToast(SearchActivity.this,"对不起，未能找到匹配");
                            }
                            adapter.addAll(searchInfo.getUsers());
                        }
                    }
                });
    }
}
