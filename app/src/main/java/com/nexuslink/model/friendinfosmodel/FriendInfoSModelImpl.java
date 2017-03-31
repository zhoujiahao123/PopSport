package com.nexuslink.model.friendinfosmodel;

import com.elvishew.xlog.XLog;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.util.ApiUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/3/5.
 */

public class FriendInfoSModelImpl implements FriendInfoSModel {
    @Override
    public void getUserInfo( int uId, final OnUserCallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .getUserInfo(uId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        XLog.e("请求用户信息的时候失败了     原因:"+e.toString());
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        listener.onSucceed(userInfo);
                    }
                });
    }

    @Override
    public void getFollowInfo(int uId, final OnUserCallBackListener listener) {
            ApiUtil.getInstance(Constants.BASE_URL)
                    .getFollowedInfo(uId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<FollowedInfo>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            XLog.e("请求关注的人的信息的时候失败了     原因:"+e.toString());
                        }

                        @Override
                        public void onNext(FollowedInfo followedInfo) {
                            listener.onSucceed(followedInfo);
                        }
                    });
    }

    @Override
    public void getError() {

    }
}
