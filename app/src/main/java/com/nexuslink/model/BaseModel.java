package com.nexuslink.model;

/**
 * Created by ASUS-NB on 2017/1/17.
 */

/**
 * 约定Model下所有方法以get起,例如getMethod;
 */
public interface BaseModel {

    void getError();
    interface OnCallBackListener{
        void onSucceed(Object o);
        void onFailed(Throwable t);
    }
}
