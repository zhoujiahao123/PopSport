package com.nexuslink.model.friendmodel;

import com.elvishew.xlog.XLog;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FollowInfo;
import com.nexuslink.model.data.SearchInfo;
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
                        listener.onFailed(e);
                    }

                    @Override
                    public void onNext(FollowInfo followInfo) {
                        listener.onSucceed(followInfo);
                    }
                });

    }

    @Override
    public void searchUser(int type, String name, final OnFriendCallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .searchUser(type,name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(e);
                        XLog.e("失败了");
                    }

                    @Override
                    public void onNext(SearchInfo searchInfo) {
                        XLog.e("成功了");
                        listener.onSucceed(searchInfo);
                    }
                });
    }

    @Override
    public void getError() {

    }
}
