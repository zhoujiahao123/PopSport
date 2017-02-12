package com.nexuslink.model.communitymodel;

import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.util.ApiUtil;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 猿人 on 2017/2/12.
 */

public class CommunityModelImpl implements CommunityModel{


    @Override
    public void getArticles(int userId, final CallBackListener listener) {
        Api api = ApiUtil.getInstance(Constants.BASE_URL);
        api.getArticles(userId,0).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<List<CommunityInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onError((Exception) e);
            }

            @Override
            public void onNext(List<CommunityInfo> communityInfos) {
                listener.onFinish(communityInfos);
            }
        });
    }

    @Override
    public void postLike(int userId, int articleId, CallBackListener listener) {

    }

    @Override
    public void postComment(int userId, int articleId, String text, CallBackListener listener) {

    }
}
