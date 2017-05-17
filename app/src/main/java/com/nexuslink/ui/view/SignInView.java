package com.nexuslink.ui.view;

/**
 * Created by ASUS-NB on 2016/12/20.
 */

public interface SignInView extends BaseView{
    //向服务器提交注册账号信息
    void requestSignIn(String uName,String uPassword,char uGender,int uHeight,int uWeight,String imagePath);

    //注册失败时实现
    void failedSignIn(String message);

    //注册成功时实现
    void succeedSignIn();
}
