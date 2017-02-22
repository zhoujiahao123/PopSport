package com.nexuslink.model.altermodel;

import android.util.Log;

import com.elvishew.xlog.XLog;
import com.nexuslink.User;
import com.nexuslink.UserDao;
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
        XLog.e("这里获得的id为:"+uId);
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
                        Log.e("ATG",e.toString());
                        listener.onFailed(e);
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        listener.onSucceed(userInfo);
                        User user = BaseApplication.getDaosession().getUserDao().queryBuilder().where(UserDao.Properties.Already.eq(1)).unique();
                        String achievement=new String();
                        for(int i =0;i<8;i++){
                            achievement+= String.valueOf(userInfo.getUser().getUAchievements()[i]);
                        }
                        user.setUAchievements(achievement.substring(1,achievement.length()-1));
                        user.setUExp(userInfo.getUser().getUExp());
                        user.setUFansNum(userInfo.getUser().getUFansNum());
                        user.setUGender(userInfo.getUser().getUGender());
                        user.setUHeight(userInfo.getUser().getUHeight());
                        user.setUImg(userInfo.getUser().getUImg());
                        user.setUHistoryMileage(Long.valueOf(userInfo.getUser().getUHistoryMileage()));
                        user.setUHistoryStep(Long.valueOf(userInfo.getUser().getUHistoryStep()));
                        user.setUName(userInfo.getUser().getUName());
                        user.setUWeight(userInfo.getUser().getUWeight());
                        BaseApplication.getDaosession().getUserDao().update(user);
                    }
                });
    }

    @Override
    public void changeUserInfo(int uId, final char uGender, final int uHeight, final int uWeight, final com.nexuslink.model.altermodel.OnCallBackListener listener) {
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
                        XLog.e(e.getStackTrace());
                        listener.onFailed(e);
                    }

                    @Override
                    public void onNext(ChangeInfo changeInfo) {
                        listener.onSucceed(changeInfo);
                        UserDao userDao = BaseApplication.getDaosession().getUserDao();
                        User user = userDao.queryBuilder().where(UserDao.Properties.Already.eq(1)).unique();
                        user.setUGender(String.valueOf(uGender));
                        user.setUWeight(uWeight);
                        user.setUHeight(uHeight);
                        userDao.update(user);
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
