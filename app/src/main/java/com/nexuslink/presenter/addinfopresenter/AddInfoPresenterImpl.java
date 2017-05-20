package com.nexuslink.presenter.addinfopresenter;

import com.nexuslink.model.addinfomodel.AddInfoModel;
import com.nexuslink.model.addinfomodel.OnAddInfoCallBackListner;
import com.nexuslink.ui.view.OtherLogInAddInfoView;

/**
 * Created by ASUS-NB on 2017/5/20.
 */

public class AddInfoPresenterImpl extends AddInfoPresenter implements OnAddInfoCallBackListner{

    AddInfoModel model;
    OtherLogInAddInfoView view;
    public AddInfoPresenterImpl(AddInfoModel model,OtherLogInAddInfoView view){
        this.model = model;
        this.view = view;
    }
    @Override
    public void addInfo(int uId, char uGender, int uHeight, int uWeight) {
        model.addInfo(uId,uGender,uHeight,uWeight,this);
    }

    @Override
    public void onSucceed(Object o) {
        view.onAddSucceed();
    }

    @Override
    public void onFailed(Throwable t) {

    }
}
