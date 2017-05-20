package com.nexuslink.presenter.loginpresenter;

import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.loginmodel.LogInModeImp;
import com.nexuslink.model.loginmodel.LogInModel;
import com.nexuslink.ui.view.LoginView;

import java.io.File;


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

    @Override
    public void otherLogIn(String openId, String uName, char uGender, File uImg) {
        logInModel.getOtherLogInInfo(openId, uName, uGender, uImg, new CallBackListener() {
            @Override
            public void onFinish(Object data) {
                loginView.loginSuccess();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
