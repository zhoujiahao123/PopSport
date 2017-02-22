package com.nexuslink.model.signinmodel;

import com.nexuslink.model.BaseModel;

/**
 * Created by ASUS-NB on 2017/2/19.
 */

public interface SignInModel  extends BaseModel{
    void requestRegister(String uName, String uPassword, char uGender, int uHeight, int uWeight, com.nexuslink.model.signinmodel.OnCallBackListener listener);
}
