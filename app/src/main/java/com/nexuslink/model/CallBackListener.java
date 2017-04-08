package com.nexuslink.model;

/**
 * Created by ASUS-NB on 2017/2/19.
 */

public interface CallBackListener<T> {
    void onFinish(T data);

    void onError(Exception e);


}
