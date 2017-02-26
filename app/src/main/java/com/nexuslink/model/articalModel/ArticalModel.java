package com.nexuslink.model.articalModel;

import com.nexuslink.model.BaseModel;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public interface ArticalModel extends BaseModel {
    void getArtical(int uId, int writeId, com.nexuslink.model.articalModel.OnCallBackListener listener);
}
