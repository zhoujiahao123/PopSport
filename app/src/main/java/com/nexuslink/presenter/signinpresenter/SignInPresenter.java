package com.nexuslink.presenter.signinpresenter;

import com.nexuslink.model.data.UIdInfo;
import com.nexuslink.model.signinmodel.SignInModel;
import com.nexuslink.presenter.BasePresenter;
import com.nexuslink.ui.view.SignInView;

/**
 * Created by ASUS-NB on 2017/2/19.
 */

public abstract class SignInPresenter extends BasePresenter<SignInModel,SignInView> {
    public abstract void registerSucceed(UIdInfo uIdInfo);

    public abstract void requestRegister(String uName,String uPassword,char uGender,int uHeight,int uWeight,String imagePath);
}
