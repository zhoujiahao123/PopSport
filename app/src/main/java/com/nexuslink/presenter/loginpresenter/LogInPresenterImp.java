package com.nexuslink.presenter.loginpresenter;

import android.util.Log;

import com.nexuslink.config.Constants;
import com.nexuslink.model.loginmodel.LogInModeImp;
import com.nexuslink.model.loginmodel.LogInModel;
import com.nexuslink.ui.view.LoginView;


/**
 * Created by ASUS-NB on 2016/12/19.
 */

public class LogInPresenterImp extends LogInPresenter{
    private LoginView loginView;
    private LogInModel logInModel;
    public LogInPresenterImp(LoginView loginView){
        logInModel = new LogInModeImp(this);
        this.loginView = loginView;
    }



    @Override
    public void logInToService(String uName,String password) {
        Log.e(Constants.TAG,"logInToService");
        logInModel.getLogInInfo(uName,password);
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
