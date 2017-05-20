package com.nexuslink.presenter.addinfopresenter;

import com.nexuslink.model.addinfomodel.AddInfoModel;
import com.nexuslink.model.addinfomodel.OnAddInfoCallBackListner;
import com.nexuslink.presenter.BasePresenter;
import com.nexuslink.ui.view.OtherLogInAddInfoView;

/**
 * Created by ASUS-NB on 2017/5/20.
 */

public abstract class AddInfoPresenter extends BasePresenter<AddInfoModel,OtherLogInAddInfoView>{
    public abstract void addInfo(int uId, char uGender, int uHeight, int uWeight);
}
