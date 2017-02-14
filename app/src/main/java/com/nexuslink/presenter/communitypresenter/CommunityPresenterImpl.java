package com.nexuslink.presenter.communitypresenter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.communitymodel.CommunityModel;
import com.nexuslink.model.communitymodel.CommunityModelImpl;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.model.data.User1;
import com.nexuslink.ui.view.CommunityView;
import com.nexuslink.ui.view.UserUtils;

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
    public void postComment(final LinearLayout commentDetialLinear, final View view, final EditText input, final LinearLayout linearLayout, int userId, int articleId, final int pos) {
        if(!mCommunityView.getInputComment(input).equals("")){
            mCommunity.postComment(userId, articleId,mCommunityView.getInputComment(input), new CallBackListener() {
                @Override
                public void onFinish(Object obj) {
                    mCommunityView.addCommentNum(pos);
                    //getUserName 获取自己的昵称
                    mCommunityView.addOneComment(commentDetialLinear,view,"张兴锐",mCommunityView.getInputComment(input));
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

    @Override
    public void onRefreshData(int userId) {

        mCommunity.getArticles(userId, new CallBackListener() {
            @Override
            public void onFinish(Object obj) {
                mCommunityView.showSuccess("刷新成功");
                mCommunityView.addMsgArticle((List<CommunityInfo.ArticlesBean>) obj);
            }

            @Override
            public void onError(Exception e) {
                mCommunityView.showError("刷新失败,请重试");
                e.printStackTrace();
            }
        });
    }

    @Override
    public void loadUserInfo(final ImageView imageView, final TextView nameText, final TextView levelText, int userId) {
        mCommunity.loadUserInfo(userId, new CallBackListener() {
            @Override
            public void onFinish(Object obj) {
                User1 user = (User1) obj;
                mCommunityView.loadUserInfo(imageView,nameText,levelText, Constants.PHOTO_BASE_URL+user.getUImg(),user.getUName()
                , UserUtils.getUserLevel());
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void loadComment(int articleId, final int pos) {
        mCommunity.getComment(articleId, new CallBackListener() {
            @Override
            public void onFinish(Object obj) {
                mCommunityView.addCommentNum(pos);

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }


}
