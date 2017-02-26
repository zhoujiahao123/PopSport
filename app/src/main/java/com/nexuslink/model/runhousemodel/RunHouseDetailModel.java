package com.nexuslink.model.runhousemodel;

import com.nexuslink.model.CallBackListener;

/**
 * Created by 猿人 on 2017/2/25.
 */

public interface RunHouseDetailModel {
    void joinRoom(int roomId, CallBackListener listener);
    void quitRoom(int rId,CallBackListener listener);
}
