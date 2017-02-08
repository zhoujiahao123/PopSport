package com.nexuslink.model.friendinfomodel;

import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FriendInfo;
import com.nexuslink.util.ApiUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/2/8.
 */

public class FriendInfoModelImpl implements FriendInfoModel {
    @Override
    public void getError() {

    }

    @Override
    public void getFriendInfo(int fId, final com.nexuslink.model.friendinfomodel.OnCallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .getFriendInfo(fId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FriendInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(e);

                    }

                    @Override
                    public void onNext(FriendInfo friendInfo) {
                        listener.onSucceed(friendInfo);

                    }
                });
    }
}
