package com.nexuslink.ui.fragment;

import android.support.v7.widget.GridLayoutManager;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.nexuslink.R;
import com.nexuslink.model.data.FansInfo;
import com.nexuslink.presenter.personfriendpresenter.FriendPresenterImpl;
import com.nexuslink.ui.adapter.FansRecyclerAdapter;
import com.nexuslink.ui.view.ContainPresenterFragment;
import com.nexuslink.ui.view.IFansView;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by 猿人 on 2017/4/8.
 */

public class FansFragment extends ContainPresenterFragment implements IFansView<FansInfo.FansBean> {
    /**
     * -
     * view
     */
    @BindView(R.id.friends_recycler)
    EasyRecyclerView mRecyclerView;
    /**
     * 数据
     */
    private FansRecyclerAdapter adapter;
    private FriendPresenterImpl presenter;

    @Override
    public int getLayout() {
        return R.layout.friends_list_fragment;
    }

    @Override
    public void initView() {
        mRecyclerView.setProgressView(R.layout.progress_layout);
        mRecyclerView.setEmptyView(R.layout.empty_view);
        mRecyclerView.setErrorView(R.layout.peron_article_error);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4,GridLayoutManager.VERTICAL,false));

        adapter = new FansRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void initPresenter() {
        presenter = new FriendPresenterImpl();
        presenter.onCreate();
        presenter.attachView(this);
        //请求数据
        presenter.getFansInfo(UserUtils.getUserId());
    }



    @Override
    public void showProgress() {
        mRecyclerView.showProgress();
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {
        ToastUtil.showToast(getContext(), message);
    }

    @Override
    public void setDatas(List<FansInfo.FansBean> datas) {
        adapter.addAll(datas);
    }


}
