package com.nexuslink.model.runhousemodel;

import com.nexuslink.model.CallBackListener;

import java.util.Date;

/**
 * Created by 猿人 on 2017/2/25.
 */

public interface CreateRunHouseModel {
    void createRunHouse(int type, int goal, String name, Date date, CallBackListener listener);
}
