package com.nexuslink.model.articalModel;

import com.nexuslink.config.Constants;
import com.nexuslink.model.data.AticalInfo;
import com.nexuslink.util.ApiUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public class AriticalModelImpl implements ArticalModel {
    @Override
    public void getArtical(int uId, int writeId, final com.nexuslink.model.articalModel.OnCallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .getAtical(uId,writeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AticalInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(e);
                    }

                    @Override
                    public void onNext(AticalInfo aticalInfo) {
                        listener.onSucceed(aticalInfo);
                    }
                });
    }

    @Override
    public void getError() {

    }
}
