package com.nexuslink.model.addinfomodel;

import com.nexuslink.config.Constants;
import com.nexuslink.model.data.ChangeInfo;
import com.nexuslink.util.ApiUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/5/20.
 */

public class AddInfoModelImpl implements AddInfoModel {
    @Override
    public void getError() {

    }

    @Override
    public void addInfo(int uId, char uGender, int uHeight, int uWeight, final OnAddInfoCallBackListner listner) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .changeUserInfo(uId,uGender,uHeight,uWeight)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChangeInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ChangeInfo changeInfo) {
                        if(changeInfo.getCode()!=500){
                            listner.onSucceed("");
                        }
                    }
                });


    }
}
