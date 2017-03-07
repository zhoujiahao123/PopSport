package com.nexuslink.model.runhousemodel;

import com.nexuslink.model.CallBackListener;

/**
 * Created by 猿人 on 2017/2/25.
 */

public interface CreateRunHouseModel {
    void createRunHouse(int type, int goal, String name, String date, CallBackListener listener);
}
