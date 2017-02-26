package com.nexuslink.model.fansmodel;

import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FansInfo;
import com.nexuslink.util.ApiUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public class FansModelImpl implements FansModel {
    @Override
    public void getFans(int uId, final OnCallbackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .getFans(uId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FansInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(e);
                    }

                    @Override
                    public void onNext(FansInfo fansInfo) {
                        listener.onSucceed(fansInfo);
                    }
                });
    }

    @Override
    public void getError() {

    }
}
