package com.umeng.soexample.presenter;

import android.widget.Toast;

import com.umeng.soexample.app.BaseApplication;
import com.umeng.soexample.model.LogInModeImp;
import com.umeng.soexample.model.LogInModel;
import com.umeng.soexample.ui.LogInActivity;
import com.umeng.soexample.ui.view.LoginView;

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
        logInModel.sendRequestForLogIn();
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
