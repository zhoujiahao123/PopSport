package com.nexuslink.presenter.communitypresenter;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nexuslink.app.BaseApplication;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.communitymodel.CommunityModel;
import com.nexuslink.model.communitymodel.CommunityModelImpl;
import com.nexuslink.model.data.CommentInfo;
import com.nexuslink.model.data.CommentItemData;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.ui.view.CommentsList;
import com.nexuslink.ui.view.CommunityView;
import com.nexuslink.util.UserUtils;
import com.nexuslink.util.cache.DiskLruCacheHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 猿人 on 2017/2/12.
 */

public class CommunityPresenterImpl implements CommunityPresenter {
    private CommunityModel mCommunity;
    private CommunityView mCommunityView;
    private DiskLruCacheHelper helper = BaseApplication.helper;
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
    public void postComment(final CommentsList commentsList, final EditText input, final LinearLayout linearLayout, int userId, final int articleId, final int pos)
    {
        if(!TextUtils.isEmpty(input.getText().toString())){

            mCommunity.postComment(userId, articleId,input.getText().toString(), new CallBackListener() {
                @Override
                public void onFinish(Object obj) {

                    mCommunityView.addCommentNum(pos);

                    //刷新缓存
                    List<CommentItemData> commentItemDatas = helper.getAsSerializable(articleId+"comments");

                    helper.remove(articleId+"comments");
                    commentItemDatas.add(new CommentItemData(UserUtils.getUserName(),input.getText().toString()));
                    helper.put(articleId+"comments", (Serializable) commentItemDatas);

                    //回调接口
                    mCommunityView.setCommentsList(commentsList,articleId,commentItemDatas);

                    mCommunityView.clearInput(linearLayout,input);

                }

                @Override
                public void onError(Exception e) {
                    mCommunityView.showError("评论时出错，请重试");
                }
            });
        }else{
            mCommunityView.showError("输入内容不能为空哦");
        }

    }

    /***
     * 刷新
     * @param userId
     */
    @Override
    public void onRefreshData(int userId) {
        mCommunityView.showProgress();
        mCommunity.getArticles(userId, new CallBackListener() {
            @Override
            public void onFinish(Object obj) {

                mCommunityView.showSuccess("刷新成功");
                mCommunityView.hideProgress();
                mCommunityView.addMsgArticle((List<CommunityInfo.ArticlesBean>) obj);
            }

            @Override
            public void onError(Exception e) {
                mCommunityView.showError("刷新失败,请重试");
                mCommunityView.hideProgress();
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onLoadMore(int aId) {
        mCommunity.loadMore(aId, new CallBackListener() {
            @Override
            public void onFinish(Object o) {
                List<CommunityInfo.ArticlesBean> list = (List<CommunityInfo.ArticlesBean>) o;
                if(list == null || list.size() == 0){
                    mCommunityView.showSuccess("无更多话题");
                }
                mCommunityView.addCommunityItems((List<CommunityInfo.ArticlesBean>) o);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }


    /**
     * 加载评论
     */
    @Override
    public void loadComment(final CommentsList commentDetialLinear, final int articleId, final int pos) {
        mCommunity.getComments(articleId, new CallBackListener() {
            @Override
            public void onFinish(Object obj) {

                 List<CommentInfo.CommentsBean> commentsBean = (List<CommentInfo.CommentsBean>) obj;
                 List<CommentItemData> commentItemDatas = new ArrayList<CommentItemData>();
                for(int i =0;i<commentsBean.size();i++){
                    CommentInfo.CommentsBean commentsBean1 = commentsBean.get(i);
                    CommentItemData commentItemData = new CommentItemData(commentsBean1.getUserName(),commentsBean1.getCommentText());
                    commentItemDatas.add(commentItemData);
                }
                //进行缓存
                helper.put(articleId+"comments", (Serializable) commentItemDatas);
                //设置adpater
                mCommunityView.setCommentAdapter(commentDetialLinear,articleId,commentItemDatas);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

        });
    }


}
