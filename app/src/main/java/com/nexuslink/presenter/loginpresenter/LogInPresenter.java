package com.nexuslink.presenter.loginpresenter;

import com.nexuslink.model.loginmodel.LogInModel;
import com.nexuslink.presenter.BasePresenter;
import com.nexuslink.ui.view.LoginView;

/**
 * Created by ASUS-NB on 2016/12/19.
 */

public abstract class LogInPresenter extends BasePresenter<LogInModel,LoginView> {
    //向服务器发起请求
    public abstract void logInToService(String uName, String password);
    //服务器返回的结果
    public abstract void logInCheck(boolean trueOrFalse);
}
