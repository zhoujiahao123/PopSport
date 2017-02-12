package com.nexuslink.presenter.communitypresenter;

import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.communitymodel.CommunityModel;
import com.nexuslink.model.communitymodel.CommunityModelImpl;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.ui.view.CommunityView;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/12.
 */

public class CommunityPresenterImpl implements CommunityPresenter {
    private CommunityModel mCommunity;
    private CommunityView mCommunityView;

    public CommunityPresenterImpl(CommunityView mCommunityView) {
        this.mCommunityView = mCommunityView;
        mCommunity = new CommunityModelImpl();
    }

    @Override
    public void postLike(int userId, int articleId) {
        mCommunity.postLike(userId, articleId, new CallBackListener() {
            @Override
            public void onFinish(Object obj) {
                mCommunityView.showSuccess("点赞成功");
            }

            @Override
            public void onError(Exception e) {
                mCommunityView.showError("点赞失败，请检查网络...");
            }
        });
    }

    @Override
    public void postDisLike(int userId, int articleId) {
        mCommunity.postDisLike(userId, articleId, new CallBackListener() {
            @Override
            public void onFinish(Object obj) {
                mCommunityView.showSuccess("成功取消");
            }

            @Override
            public void onError(Exception e) {
                mCommunityView.showError("取消失败，请重试");
            }
        });
    }

    @Override
    public void postComment(int userId, int articleId, final String text, final int pos) {
        mCommunity.postComment(userId, articleId, text, new CallBackListener() {
            @Override
            public void onFinish(Object obj) {
                mCommunityView.addCommentNum(pos);
                //getUserName 获取自己的昵称
                mCommunityView.addOneComment("张兴锐",text);
            }

            @Override
            public void onError(Exception e) {
                if(e == null){
                    mCommunityView.showError("输入不能为空哦...");
                }else{
                    mCommunityView.showError("评论时出错，重试");
                }

            }
        });
    }

    @Override
    public void onRefreshData(int userId) {
        mCommunity.getArticles(userId, new CallBackListener() {
            @Override
            public void onFinish(Object obj) {
                mCommunityView.showSuccess("刷新成功");
                mCommunityView.addMsgArticle((List<CommunityInfo.CommunityBean>) obj);
            }

            @Override
            public void onError(Exception e) {
                mCommunityView.showError("刷新失败,请重试");
            }
        });
    }
}
