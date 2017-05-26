package com.nexuslink.model.addinfomodel;

import com.elvishew.xlog.XLog;
import com.nexuslink.User;
import com.nexuslink.UserDao;
import com.nexuslink.app.BaseApplication;
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
    public void addInfo(int uId, char uGender, final int uHeight, final int uWeight, final OnAddInfoCallBackListner listner) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .changeUserInfo(uId,uGender,uHeight,uWeight)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChangeInfo>() {
                    @Override
                    public void onCompleted() {
                        XLog.e("修改完成了哒");
                    }

                    @Override
                    public void onError(Throwable e) {
                        XLog.e("修改失败了哒");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ChangeInfo changeInfo) {
                        XLog.e("修改成功了哒"+changeInfo.getCode());
                        if(changeInfo.getCode()!=500){
                            User user = BaseApplication.getDaosession().getUserDao().queryBuilder().where(UserDao.Properties.Already.eq(1)).unique();
                            user.setUHeight(uHeight);
                            user.setUWeight(uWeight);
                            BaseApplication.getDaosession().getUserDao().update(user);
                            listner.onSucceed("");
                        }
                    }
                });


    }
}
