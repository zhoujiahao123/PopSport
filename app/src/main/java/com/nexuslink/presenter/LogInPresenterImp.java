package com.nexuslink.presenter;

import com.nexuslink.model.LogInModeImp;
import com.nexuslink.model.LogInModel;
import com.nexuslink.ui.activity.LogInActivity;
import com.nexuslink.ui.view.LoginView;


/**
 * Created by ASUS-NB on 2016/12/19.
 */

public class LogInPresenterImp implements LogInPresenter{
    private LoginView loginView;
    private LogInModel logInModel;
    public LogInPresenterImp(){
        loginView = new LogInActivity();
        logInModel = new LogInModeImp(this);
    }
    @Override
    public void logInToService(String uid,String password) {
        logInModel.getLogInInfo();
    }

    @Override
    public void logInCheck(boolean trueOrFalse) {
        if(trueOrFalse){
            loginView.succeedLogIn();
        }else {
            loginView.failedLogIn();
        }
    }
}
