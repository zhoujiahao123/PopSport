package com.nexuslink.model.runhousemodel;

import com.nexuslink.model.CallBackListener;

/**
 * Created by 猿人 on 2017/2/5.
 */

public interface RunHouseModel<T> {
    void onRefresh(int startId, CallBackListener<T> listener);
    void getMyRooms(int uId,CallBackListener<T> listener);

}
