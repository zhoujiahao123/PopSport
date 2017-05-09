package com.nexuslink.presenter.signinpresenter;

import com.nexuslink.model.data.UIdInfo;
import com.nexuslink.model.signinmodel.OnCallBackListener;
import com.nexuslink.model.signinmodel.SignInModel;
import com.nexuslink.ui.view.SignInView;

/**
 * Created by ASUS-NB on 2017/2/19.
 */

public class SignInPresenterImpl extends SignInPresenter implements OnCallBackListener{
    private SignInModel model;
    private SignInView view;

    public SignInPresenterImpl(SignInModel signInModel,SignInView signInView){
        model = signInModel;
        view = signInView;
    }
    @Override
    public void registerSucceed(UIdInfo uIdInfo) {
        view.succeedSignIn();
    }



    @Override
    public void requestRegister(String uName, String uPassword, char uGender, int uHeight, int uWeight,String imagePath) {
        model.requestRegister(uName,uPassword,uGender,uHeight,uWeight,imagePath,this);
    }

    @Override
    public void onSucceed(Object o) {
        registerSucceed(null);
    }

    @Override
    public void onFailed(Throwable t) {
        if(t.getMessage().equals("用户名已存在")){
            view.failedSignIn("用户名已存在");
        }else if(t.getMessage().equals("服务器出现错误")){
            view.failedSignIn("服务器出现错误");
        }else {
            view.failedSignIn("未知的错误发生了");
        }

    }
}
