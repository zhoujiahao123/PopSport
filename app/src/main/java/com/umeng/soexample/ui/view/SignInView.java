package com.umeng.soexample.ui.view;

/**
 * Created by ASUS-NB on 2016/12/20.
 */

public interface SignInView{
    //向服务器提交注册账号信息
    void requestSignIn();

    //注册失败时实现
    void failedSignIn();

    //注册成功时实现
    void succeedSignIn();
}
