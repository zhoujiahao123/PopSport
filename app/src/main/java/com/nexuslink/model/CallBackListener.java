package com.nexuslink.model;

/**
 * Created by ASUS-NB on 2017/2/19.
 */

public interface CallBackListener {
    void onFinish(Object o);

    void onError(Exception e);
}
