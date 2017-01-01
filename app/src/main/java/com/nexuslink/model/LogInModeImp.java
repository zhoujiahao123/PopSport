package com.nexuslink.model;


import com.nexuslink.presenter.LogInPresenterImp;

/**
 * Created by ASUS-NB on 2016/12/19.
 */

public class LogInModeImp implements LogInModel {
    private LogInPresenterImp presenterImp;

    public LogInModeImp(LogInPresenterImp presenterImp) {
        this.presenterImp = presenterImp;
    }

    @Override
    public void sendRequestForLogIn() {
        //根据服务器返回的数据来判断是否成功
        presenterImp.logInCheck(true);
    }
}
