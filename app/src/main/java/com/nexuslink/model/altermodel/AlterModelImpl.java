package com.nexuslink.model.altermodel;

import android.util.Log;

import com.nexuslink.config.Constants;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.util.ApiUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/2/9.
 */

public class AlterModelImpl implements AlterModel {

    @Override
    public void getUserInfo(int uId, final com.nexuslink.model.altermodel.OnCallBackListener listener) {
        Log.e("TAG","getUserInfo");
        ApiUtil.getInstance(Constants.BASE_URL)
                .getUserInfo(8)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ATG",e.toString());
                        listener.onFailed(e);
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        listener.onSucceed(userInfo);
                    }
                });
    }

    @Override
    public void getError() {

    }
}
