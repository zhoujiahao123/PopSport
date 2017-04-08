package com.nexuslink.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.nexuslink.R;
import com.nexuslink.model.data.ArticleBean;
import com.nexuslink.model.data.CommentItemData;
import com.nexuslink.presenter.communitypresenter.CommunityPresenter;
import com.nexuslink.presenter.communitypresenter.CommunityPresenterImpl;
import com.nexuslink.ui.adapter.PersonArticleAdapter;
import com.nexuslink.ui.view.CommunityView;
import com.nexuslink.util.UserUtils;
import com.vanniktech.emoji.EmojiTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 猿人 on 2017/4/8.
 */

public class PersonArticleFragment extends Fragment implements CommunityView<ArticleBean.ArticlesBean> {
    /**
     * view
     */
    @BindView(R.id.person_article_recycler_view)
    EasyRecyclerView mRecylerView;
    Unbinder unbinder;

    /**
     * 数据
     */
    private PersonArticleAdapter adapter;
    private CommunityPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CommunityPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_article_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        adapter = new PersonArticleAdapter(getContext(), presenter);
        mRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecylerView.setProgressView(R.layout.person_article_loadding_view);
        mRecylerView.setEmptyView(R.layout.empty_view);
        mRecylerView.setErrorView(R.layout.peron_article_error);
        mRecylerView.setAdapter(adapter);

        //开始加载数据
        presenter.getHis(UserUtils.getUserId(), UserUtils.getUserId());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showSuccess(String str) {
    }

    @Override
    public void showError(String str) {
        mRecylerView.showError();
    }

    @Override
    public void clearInput(LinearLayout linearLayout, EditText input) {
        input.setText("");
        linearLayout.setVisibility(View.GONE);
        //设置button可点击
        linearLayout.getChildAt(1).setClickable(true);
        linearLayout.getChildAt(1).setBackground(getResources().getDrawable(R.drawable.bt_run_house_normal));
    }


    @Override
    public void addMsgArticle(List<ArticleBean.ArticlesBean> list) {
        if (list == null || list.isEmpty()) {
            mRecylerView.showEmpty();
            return;
        }
        adapter.setDatas(list);

    }

    @Override
    public void setCommentsList(LinearLayout commentsList, int aId, List<CommentItemData> commentsLists) {
        commentsList.removeAllViews();
        for (int i = 0; i < commentsLists.size(); i++) {
            CommentItemData commentItemData = commentsLists.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.comment_item, null);
            SpannableString msg = new SpannableString(commentItemData.getUserName() + ":" + commentItemData.getCommentText());
            msg.setSpan(new ForegroundColorSpan(0xff6b8747), 0, commentItemData.getUserName().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            EmojiTextView tv = (EmojiTextView) view.findViewById(R.id.comment);
            tv.setText(msg);
            commentsList.addView(view);
        }
    }

    @Override
    public void setCommentAdapter(LinearLayout commentListView, int aId, List<CommentItemData> list) {
        for (int i = 0; i < list.size(); i++) {
            CommentItemData commentItemData = list.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.comment_item, null);
            SpannableString msg = new SpannableString(commentItemData.getUserName() + ":" + commentItemData.getCommentText());
            msg.setSpan(new ForegroundColorSpan(0xff6b8747), 0, commentItemData.getUserName().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            EmojiTextView tv = (EmojiTextView) view.findViewById(R.id.comment);
            tv.setText(msg);
            commentListView.addView(view);
        }
    }

    @Override
    public void addCommentNum(int pos) {
        adapter.addCommentNum(pos);
    }

    @Override
    public void showProgress() {
        mRecylerView.showProgress();
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void addCommunityItems(List<ArticleBean.ArticlesBean> list) {
        adapter.addItems(list);
    }
}
