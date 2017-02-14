package com.nexuslink.model.altermodel;

import com.nexuslink.model.BaseModel;

/**
 * Created by ASUS-NB on 2017/2/9.
 */

public interface AlterModel extends BaseModel {
    void getUserInfo(int uId, com.nexuslink.model.altermodel.OnCallBackListener listener);

    void changeUserInfo(int uId, char uGender, float uHeight, float uWeight, com.nexuslink.model.altermodel.OnCallBackListener listener);

    void changeNickName(int uId, String uName, com.nexuslink.model.altermodel.OnCallBackListener listener);

    void changePassword(int uId,String oldPassword,String newPassword);

}
