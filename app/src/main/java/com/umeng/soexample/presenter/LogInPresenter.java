package com.umeng.soexample.presenter;

/**
 * Created by ASUS-NB on 2016/12/19.
 */

public interface LogInPresenter {
    //向服务器发起请求
    void logInToService(String uid,String password);
    //服务器返回的结果
    void logInCheck(boolean trueOrFalse);
}
