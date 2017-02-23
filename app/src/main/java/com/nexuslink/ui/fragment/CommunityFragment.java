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
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nexuslink.R;
import com.nexuslink.model.data.CommentItemData;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.presenter.communitypresenter.CommunityPresenter;
import com.nexuslink.presenter.communitypresenter.CommunityPresenterImpl;
import com.nexuslink.ui.activity.WriteMsgActivity;
import com.nexuslink.ui.adapter.CommentsAdapter;
import com.nexuslink.ui.adapter.CommunityRecyclerAdapter;
import com.nexuslink.ui.view.CommentsList;
import com.nexuslink.ui.view.CommunityView;
import com.nexuslink.ui.view.view.headerview.LoadingView;
import com.nexuslink.ui.view.view.headerview.RunHouseFooter;
import com.nexuslink.ui.view.view.headerview.RunHouseHeader;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static com.nexuslink.app.BaseApplication.mContext;

/**
 * Created by 猿人 on 2017/1/14.
 */

public class CommunityFragment extends Fragment implements CommunityView {
    //===============================================view
    @BindView(R.id.toolbar_community)
    Toolbar mToolbar;
    @BindView(R.id.recycler_community)
    RecyclerView mRecycler;
    @BindView(R.id.ptrFrame)
    PtrFrameLayout ptrFrame;
    @BindView(R.id.progress_community)
    LoadingView progress;
    //===============================================一数据
    private AppCompatActivity compatActivity;
    private CommunityRecyclerAdapter adapter;
    private CommunityPresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compatActivity = (AppCompatActivity) getActivity();
        presenter = new CommunityPresenterImpl(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_fragment, container, false);

        ButterKnife.bind(this, view);

        adapter = new CommunityRecyclerAdapter(getContext(), presenter);
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置点击监听
        adapter.setUserIconClickListener(new CommunityRecyclerAdapter.UserIconClickListener() {
            @Override
            public void onClickListener(int pos) {
                //根据位置进行获取用户数据（用户id）
                long userId = adapter.getUserId(pos);
                //网络请求道个人信息界面。。。。
                ToastUtil.showToast(getContext(), "点击用户头像");
            }
        });

        //初始化时，需要进行刷新
        presenter.onRefreshData(UserUtils.getUserId());
        //设置刷新界面
        //设置下拉刷新
        //下拉刷新和上拉加载更多
        ptrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        //header
        RunHouseHeader runHouseHeader = new RunHouseHeader(getContext());
        ptrFrame.setHeaderView(runHouseHeader);
        ptrFrame.addPtrUIHandler(runHouseHeader);
        //footer
        RunHouseFooter runHouseFooter = new RunHouseFooter(getContext());
        ptrFrame.setFooterView(runHouseFooter);
        ptrFrame.addPtrUIHandler(runHouseFooter);
        //监听事件
        ptrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                //刷新
                presenter.onRefreshData(UserUtils.getUserId());
                adapter.setOnCompleteListener(new CommunityRecyclerAdapter.onCompleteListener() {
                    @Override
                    public void Completed() {
                        frame.refreshComplete();
                    }

                    @Override
                    public void Error(String msg) {
                        ToastUtil.showToast(getContext(),msg);
                    }
                });
            }

            @Override
            public void onLoadMoreBegin(final PtrFrameLayout frame) {
                List<CommunityInfo.ArticlesBean> datas = adapter.getDatas();
                presenter.onLoadMore(datas.get(datas.size()-1).getArticleId());
                adapter.setOnCompleteListener(new CommunityRecyclerAdapter.onCompleteListener() {
                    @Override
                    public void Completed() {
                        frame.refreshComplete();
                    }

                    @Override
                    public void Error(String msg) {
                        ToastUtil.showToast(getContext(),msg);
                    }
                });
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
        inflater.inflate(R.menu.community_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.write_msg) {
            Intent intent = new Intent(getActivity(), WriteMsgActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSuccess(String str) {
        ToastUtil.showToast(mContext, str);
    }

    @Override
    public void showError(String str) {
        ToastUtil.showToast(mContext, str);
    }


    @Override
    public void clearInput(LinearLayout linearLayout, EditText input) {
        input.setText("");
        linearLayout.setVisibility(View.GONE);
    }

    @Override
    public void addMsgArticle(List<CommunityInfo.ArticlesBean> list) {
        adapter.setDatas(list);
    }

    @Override
    public void setCommentsList(CommentsList commentsList, int articleId, List<CommentItemData> commentsLists) {
        CommentsAdapter adapter = (CommentsAdapter) commentsList.getAdapter();
        adapter.setDatas(commentsLists);
    }

    @Override
    public void setCommentAdapter(CommentsList listView, int articleId, List<CommentItemData> list) {
        CommentsAdapter adapter = new CommentsAdapter(list, mContext);
        listView.setAdapter(adapter);
    }

    /**
     * 增加点评量
     *
     * @param pos 指定是哪个话题的评论数量
     */
    @Override
    public void addCommentNum(int pos) {
        adapter.addCommentNum(pos);
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void addCommunityItems(List<CommunityInfo.ArticlesBean> list) {
        adapter.addItems(list);
    }


}
