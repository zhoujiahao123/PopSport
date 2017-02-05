package com.nexuslink.model;

/**
 * Created by 猿人 on 2017/2/5.
 */

public interface CallBackListener {
    void onFinish(Object obj);
    void onError(Exception e);
}
