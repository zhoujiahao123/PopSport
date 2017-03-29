package com.nexuslink.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.model.data.CommentItemData;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.presenter.communitypresenter.CommunityPresenter;
import com.nexuslink.presenter.communitypresenter.CommunityPresenterImpl;
import com.nexuslink.ui.activity.WriteMsgActivity;
import com.nexuslink.ui.adapter.CommunityRecyclerAdapter;
import com.nexuslink.ui.view.CommunityView;
import com.nexuslink.ui.view.view.headerview.LoadingView;
import com.nexuslink.ui.view.view.headerview.RunHouseFooter;
import com.nexuslink.ui.view.view.headerview.RunHouseHeader;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    PtrFrameLayout ptr1;
    PtrFrameLayout ptr2;
    //===============================================一数据
    private AppCompatActivity compatActivity;
    private CommunityRecyclerAdapter adapter;
    private CommunityPresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compatActivity = (AppCompatActivity) getActivity();
        presenter = new CommunityPresenterImpl(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onRefersh(String str){
        if(str.equals("刷新")){
            presenter.onRefreshData(UserUtils.getUserId(),true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_fragment, container, false);

        ButterKnife.bind(this, view);

        adapter = new CommunityRecyclerAdapter(getContext(), presenter);
        mRecycler.setAdapter(adapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        //初始化时，需要进行刷新
        presenter.onRefreshData(UserUtils.getUserId(),true);
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
            public void onRefreshBegin( PtrFrameLayout frame) {
                //刷新
                Log.i("刷新","刷新");
                presenter.onRefreshData(UserUtils.getUserId(),false);
                ptr1 = frame;
            }

            @Override
            public void onLoadMoreBegin( PtrFrameLayout frame) {

                List<CommunityInfo.ArticlesBean> datas = adapter.getDatas();
                presenter.onLoadMore(datas.get(datas.size()-1).getArticleId());
                ptr2 = frame;
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
        if(ptr1 != null ){
            ptr1.refreshComplete();
        }
        if(ptr2 != null){
            ptr2.refreshComplete();
        }


    }

    @Override
    public void showError(String str) {
        ToastUtil.showToast(mContext, str);
        if(ptr1 != null ){
            ptr1.refreshComplete();
        }
        if(ptr2 != null){
            ptr2.refreshComplete();
        }
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
    public void setCommentsList(LinearLayout commentsList, int articleId, List<CommentItemData> commentsLists) {
        commentsList.removeAllViews();
        for(int i = 0 ;i<commentsLists.size();i++){
            CommentItemData commentItemData = commentsLists.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.comment_item,null);
            SpannableString msg = new SpannableString(commentItemData.getUserName()+":"+commentItemData.getCommentText());
            msg.setSpan(new ForegroundColorSpan(0xff6b8747),0,commentItemData.getUserName().length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            TextView tv = (TextView)view.findViewById(R.id.comment);
            tv.setText(msg);
            commentsList.addView(view);
        }
    }

    @Override
    public void setCommentAdapter(LinearLayout listView, int articleId, List<CommentItemData> list) {
        for(int i = 0 ;i<list.size(); i++){
            CommentItemData commentItemData = list.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.comment_item,null);
            SpannableString msg = new SpannableString(commentItemData.getUserName()+":"+commentItemData.getCommentText());
            msg.setSpan(new ForegroundColorSpan(0xff6b8747),0,commentItemData.getUserName().length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            TextView tv = (TextView)view.findViewById(R.id.comment);
            tv.setText(msg);
            listView.addView(view);
        }
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
