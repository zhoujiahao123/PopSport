package com.nexuslink.model.myfriendmodel;

import com.elvishew.xlog.XLog;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.IdUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class MyFriendModelImpl implements MyFriendModel {


    @Override
    public void getFriend(final OnMyFriendCallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .getFollowedInfo((int)IdUtil.getuId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FollowedInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.failed(e);
                        XLog.e("我的好友请求失败"+e.toString());
                    }

                    @Override
                    public void onNext(FollowedInfo followedInfo) {
                        listener.succeed(followedInfo);
                        XLog.e("我的好友请求成功");
                    }
                });
    }

    @Override
    public void getError() {

    }
}
