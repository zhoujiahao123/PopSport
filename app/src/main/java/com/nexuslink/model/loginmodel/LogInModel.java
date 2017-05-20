package com.nexuslink.model.loginmodel;

import com.nexuslink.model.CallBackListener;

import java.io.File;

/**
 * Created by ASUS-NB on 2016/12/19.
 */

public interface LogInModel  {
    //向服务器发起登录请求
    void getLogInInfo(String uName, String password, CallBackListener listener);

    //第三方登录时的请求
    void getOtherLogInInfo(String openId, String uName, char uGender, File uImg,CallBackListener listener);
}
