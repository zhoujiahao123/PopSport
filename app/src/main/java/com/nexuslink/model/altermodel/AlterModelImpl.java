package com.nexuslink.model.altermodel;

import android.util.Log;

import com.elvishew.xlog.XLog;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.ChangeInfo;
import com.nexuslink.model.data.ChangeInfo1;
import com.nexuslink.model.data.ChangeInfoPassword;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.ToastUtil;

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
    public void changeUserInfo(int uId, char uGender, float uHeight, float uWeight, final com.nexuslink.model.altermodel.OnCallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .changeUserInfo(uId,uGender,(int)uHeight,(int)uWeight)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChangeInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        XLog.e(e.getStackTrace());
                        listener.onFailed(e);
                    }

                    @Override
                    public void onNext(ChangeInfo changeInfo) {
                        listener.onSucceed(changeInfo);
                    }
                });
    }

    @Override
    public void changeNickName(int uId, String uName, final com.nexuslink.model.altermodel.OnCallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .changeNickName(uId,uName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ChangeInfo1>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailed(e);
                        XLog.e("修改用户的昵称出现了错误："+e.getMessage());
                    }

                    @Override
                    public void onNext(ChangeInfo1 changeInfo) {
                        listener.onSucceed(changeInfo);
                    }
                });
    }

    @Override
    public void changePassword(int uId, String oldPassword, String newPassword) {
            ApiUtil.getInstance(Constants.BASE_URL)
                    .changePassword(uId,oldPassword,newPassword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ChangeInfoPassword>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.showToast(BaseApplication.getContext(),"未知的错误发生了");
                        }

                        @Override
                        public void onNext(ChangeInfoPassword changeInfoPassword) {
                            if(changeInfoPassword.getChangeFlag()==1){
                                ToastUtil.showToast(BaseApplication.getContext(),"密码修改成功");
                            }else if(changeInfoPassword.getChangeFlag()==0){
                                ToastUtil.showToast(BaseApplication.getContext(),"原密码输入错误");
                            }
                        }
                    });
    }

    @Override
    public void getError() {

    }
}
