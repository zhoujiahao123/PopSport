package com.nexuslink.model.loginmodel;


import android.util.Log;

import com.elvishew.xlog.XLog;
import com.nexuslink.DaoSession;
import com.nexuslink.User;
import com.nexuslink.UserDao;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.UIdInfo;
import com.nexuslink.presenter.loginpresenter.LogInPresenterImp;
import com.nexuslink.util.ApiUtil;
import com.sina.weibo.sdk.api.share.Base;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2016/12/19.
 */

public class LogInModeImp implements LogInModel {
    private LogInPresenterImp presenterImp;

    public LogInModeImp(LogInPresenterImp presenterImp) {
        this.presenterImp = presenterImp;
    }

    @Override
    public void getLogInInfo(String uName,String password) {
        Log.e(Constants.TAG,"getLogInInfo");
        Log.e(Constants.TAG,"thr uName is "+uName+"    the password is "+password);
        ApiUtil.getInstance(Constants.BASE_URL)
                .logIn(uName,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UIdInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.e(Constants.TAG,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        XLog.e(e.toString());
                    }

                    @Override
                    public void onNext(UIdInfo user) {
                        if(user.getuId()==0){
                            presenterImp.logInCheck(false);
                        }else {
                            //标志位：是否存在这一一个uid在我们的数据库里
                            int flag = 0;
                            DaoSession daoSession = BaseApplication.getDaosession();
                            UserDao  userDao = daoSession.getUserDao();
                            User user1 = new User();
                            for(int i=0;i<userDao.queryBuilder().list().size();i++){
                                if(user.getuId()==userDao.queryBuilder().list().get(i).getUid()){
                                    flag = 1;
                                }
                            }
                            if(flag==0){
                                user1.setUid(user.getuId());
                                user1.setAlready(1);
                                userDao.insert(user1);
                            }
                            presenterImp.logInCheck(true);
                        }
                    }
                });
    }

    @Override
    public void getError() {

    }
}
