package com.nexuslink.presenter.articlepresenter;

import android.text.TextUtils;

import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.articledetailsmodel.ArticleDetailModel;
import com.nexuslink.model.articledetailsmodel.ArticleDetailModelImpl;
import com.nexuslink.model.data.CommentInfo;
import com.nexuslink.model.data.SingleCommunityInfo;
import com.nexuslink.ui.view.ArticleDetailView;
import com.nexuslink.util.Base64Utils;
import com.nexuslink.util.UserUtils;

import java.util.List;


/**
 * Created by 猿人 on 2017/2/25.
 */

public class ArticleDetailPresenterImpl implements ArticleDetailPresenter {
    /**
     * view和model
     */
    private ArticleDetailModel mArticleDetailModel;
    private ArticleDetailView mArticleView;

    public ArticleDetailPresenterImpl(ArticleDetailView mArticleView) {
        this.mArticleView = mArticleView;
        mArticleDetailModel = new ArticleDetailModelImpl();
    }

    @Override
    public void loadComments(int articleId) {
        mArticleDetailModel.getComments(articleId, new CallBackListener() {
            @Override
            public void onFinish(Object o) {
                List<CommentInfo.CommentsBean> list = (List<CommentInfo.CommentsBean>) o;
                mArticleView.setCommentsView(list);
                mArticleView.clear();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void postComment(final int articleId) {
        if (!TextUtils.isEmpty(mArticleView.getCommentInput())) {
            mArticleDetailModel.postComment(UserUtils.getUserId(), articleId, Base64Utils.encode(mArticleView.getCommentInput()), new CallBackListener() {
                @Override
                public void onFinish(Object o) {
                    mArticleView.addCommentNum();
                    //进行刷新
                    loadComments(articleId);
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            mArticleView.showError("输入空时，不能进行评论哦");
        }

    }

    @Override
    public void postLike(int articleId) {
        mArticleDetailModel.postLike(UserUtils.getUserId(), articleId, new CallBackListener() {
            @Override
            public void onFinish(Object o) {
                mArticleView.showSuccess("点赞成功");
            }

            @Override
            public void onError(Exception e) {
                mArticleView.showError("点赞失败");
            }
        });
    }


    @Override
    public void loadArticle(int articleId) {
        mArticleView.showProgress();
        mArticleDetailModel.getArticle(articleId, new CallBackListener() {
            @Override
            public void onFinish(Object o) {
                mArticleView.hideProgress();
                mArticleView.setUpViews((SingleCommunityInfo.ArticleBean) o);

            }

            @Override
            public void onError(Exception e) {
                mArticleView.hideProgress();
                mArticleView.showError("加载过程中出错,请重试");
            }
        });
    }

    @Override
    public void postDisLke(int articleId) {
        mArticleDetailModel.postDisLike(UserUtils.getUserId(), articleId, new CallBackListener() {
            @Override
            public void onFinish(Object data) {

            }

            @Override
            public void onError(Exception e) {
                {
                    mArticleView.showError("取消点赞失败");
                }
            }
        });
    }
}
