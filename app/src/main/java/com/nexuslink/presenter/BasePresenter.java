package com.nexuslink.presenter;

import com.nexuslink.model.BaseModel;
import com.nexuslink.ui.view.BaseView;

/**
 * Created by ASUS-NB on 2017/1/19.
 */

public class BasePresenter<T1 extends BaseModel,T2 extends BaseView> {
    T1 model;
    T2 view;
    void setMV(T1 model,T2 view){
        this.model = model;
        this.view = view;
    }
}
