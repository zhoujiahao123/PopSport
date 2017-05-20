package com.nexuslink.ui.view;

/**
 * Created by ASUS-NB on 2017/5/20.
 */

public interface OtherLogInAddInfoView extends BaseView{
    void addInfo(int uId,char uGender,int uHeight,int uWeight);

    void onAddSucceed();
}
