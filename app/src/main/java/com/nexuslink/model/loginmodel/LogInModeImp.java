package com.nexuslink.model.loginmodel;


import android.util.Log;

import com.nexuslink.User;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.loginmodel.LogInModel;
import com.nexuslink.presenter.LogInPresenterImp;
import com.nexuslink.util.ApiUtil;

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
        ApiUtil.getInstance(Constants.BASE_URL)
                .logIn(uName,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        Log.e(Constants.TAG,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(Constants.TAG,e.toString());
                    }

                    @Override
                    public void onNext(User user) {
                        if(user.getUId()==0){
                            Log.e(Constants.TAG,"id=0");
                            presenterImp.logInCheck(false);
                        }else {
                            Log.e(Constants.TAG,"id=8");
                            User user1 = new User();
                            user1.setId(user.getId());
                            BaseApplication.getDaosession().getUserDao().insert(user1);
                            presenterImp.logInCheck(true);
                        }
                    }
                });
    }

    @Override
    public void getError() {

    }
}
