package com.nexuslink.model.loginmodel;

import com.nexuslink.model.BaseModel;

/**
 * Created by ASUS-NB on 2016/12/19.
 */

public interface LogInModel extends BaseModel {
    //向服务器发起登录请求
    void getLogInInfo(String uName,String password);
}
