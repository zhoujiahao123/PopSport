package com.nexuslink.model.addinfomodel;

import com.nexuslink.model.BaseModel;

/**
 * Created by ASUS-NB on 2017/5/20.
 */

public interface AddInfoModel extends BaseModel {
    void addInfo(int uId,char uGender,int uHeight,int uWeight,OnAddInfoCallBackListner listner);
}
