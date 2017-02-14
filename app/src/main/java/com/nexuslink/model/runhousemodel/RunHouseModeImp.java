package com.nexuslink.model.runhousemodel;

import com.nexuslink.model.CallBackListener;

/**
 * Created by 猿人 on 2017/2/5.
 */

public class RunHouseModeImp implements RunHouseModel {


    @Override
    public void loadRunHouseDetail(long id, CallBackListener callBackListener) {
        //发起请求并完成回调
        callBackListener.onFinish(null);
    }
}
