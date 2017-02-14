package com.nexuslink.model.communitymodel;

import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.CommentInfo;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.model.data.PostLikeResult;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.util.ApiUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 猿人 on 2017/2/12.
 */

public class CommunityModelImpl implements CommunityModel{

    Api api = ApiUtil.getInstance(Constants.BASE_URL);
    @Override
    public void getArticles(int userId, final CallBackListener listener) {
        api.getArticles(userId,0).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<CommunityInfo>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.onError((Exception) e);
            }

            @Override
            public void onNext(CommunityInfo communityInfo) {
                //进行flag判断
                if(communityInfo.getCode() == Constants.SUCCESS){
                    listener.onFinish(communityInfo.getArticles());
                }else {
                   listener.onError(new Exception("请求时出现错误"));
                }
            }
        });
    }

    @Override
    public void postLike(int userId, int articleId, final CallBackListener listener) {
        api.postLike(userId,articleId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<PostLikeResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onError((Exception) e);
            }
            @Override
            public void onNext(PostLikeResult result) {
                //flag 判断
                if(result.getCode() == Constants.SUCCESS){
                    listener.onFinish(null);
                }
            }
        });

    }

    @Override
    public void postDisLike(int userId, int articleId, final CallBackListener listener) {
        api.postDisLike(userId,articleId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError((Exception) e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        //flag判断回调
                        listener.onFinish(integer);
                    }
                });
    }

    @Override
    public void postComment(int userId, int articleId, String text, final CallBackListener listener) {
        api.postComment(userId,articleId,text).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommentInfo>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        listener.onError((Exception) e);
                    }
                    @Override
                    public void onNext(CommentInfo commentInfo) {
                        //进行flag判断然后回调
                        if(commentInfo.getCode() == Constants.SUCCESS){
                            listener.onFinish(commentInfo.getComments());
                        }else{
                            listener.onError(new Exception("获取评论时出错"));
                        }

                    }
                });
        }


    @Override
    public void loadUserInfo(int userId, final CallBackListener listener) {
        //进行网络的交互
        api.getUserInfo(userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        if(userInfo.getCode() == Constants.SUCCESS){
                            listener.onFinish(userInfo.getUser());
                        }else{
                            listener.onError(new Exception("加载用户信息出错"));
                        }
                    }
                });
    }

    @Override
    public void getComment(int articleId, CallBackListener listener) {

    }
}
