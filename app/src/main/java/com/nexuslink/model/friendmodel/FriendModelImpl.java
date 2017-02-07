package com.nexuslink.model.friendmodel;

import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FollowInfo;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.RetrofitUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class FriendModelImpl implements FriendModel {
    @Override
    public void followOne(int uId, int fId, final OnFriendCallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .getFollowInfo(uId,fId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FollowInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.failed(e);
                    }

                    @Override
                    public void onNext(FollowInfo followInfo) {
                        listener.succeed(followInfo);
                    }
                });

    }

    @Override
    public void getError() {

    }
}
