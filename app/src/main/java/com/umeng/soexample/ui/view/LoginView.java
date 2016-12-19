package com.umeng.soexample.ui.view;

/**
 * Created by ASUS-NB on 2016/12/19.
 */

public interface LoginView {
    //登录成功事件
    void succeedLogIn();
    //登录失败事件
    void failedLogIn();
    //登录事件
    void logIn(String uid,String password);
    //注册事件
    void signIn();
}
