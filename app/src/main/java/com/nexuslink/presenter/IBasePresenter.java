package com.nexuslink.presenter;

import com.nexuslink.ui.view.BaseView;

/**
 * Created by 猿人 on 2017/4/8.
 */

public interface IBasePresenter  {
    void attachView(BaseView view);
    void onCreate();
}
