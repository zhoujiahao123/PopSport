package com.nexuslink.presenter.loginpresenter;

import java.io.File;

/**
 * Created by 猿人 on 2017/4/24.
 */

public interface LoginPresenter  {
    void login(String uName,String uPassword);

    void otherLogIn(String openId,String uName,char uGender,File uImg);
}
