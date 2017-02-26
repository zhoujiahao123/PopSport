package com.nexuslink.model.fansmodel;

import com.nexuslink.model.BaseModel;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public interface FansModel extends BaseModel{
    void getFans(int uId,OnCallbackListener listener);
}
