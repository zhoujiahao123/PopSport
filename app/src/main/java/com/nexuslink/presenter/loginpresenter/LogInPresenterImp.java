package com.nexuslink.presenter.loginpresenter;

import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.loginmodel.LogInModeImp;
import com.nexuslink.model.loginmodel.LogInModel;
import com.nexuslink.ui.view.LoginView;


/**
 * Created by ASUS-NB on 2016/12/19.
 */

public class LogInPresenterImp implements LoginPresenter {
    private LoginView loginView;
    private LogInModel logInModel;
    public LogInPresenterImp(LoginView loginView){
        logInModel = new LogInModeImp();
        this.loginView = loginView;
    }


    @Override
    public void login(String uName, String uPassword) {
        loginView.showProgress();
        logInModel.getLogInInfo(uName, uPassword, new CallBackListener() {
            @Override
            public void onFinish(Object data) {
                loginView.hideProgress();
                loginView.loginSuccess();
            }

            @Override
            public void onError(Exception e) {
                loginView.hideProgress();
                loginView.loginFailed();
            }
        });
    }
}
