package com.nexuslink.presenter.alterpresenter;
import android.util.Log;

import com.nexuslink.model.altermodel.AlterModel;
import com.nexuslink.model.altermodel.OnCallBackListener;
import com.nexuslink.model.data.ChangeInfo;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.ui.view.AlterView;
import com.nexuslink.ui.view.BaseView;

/**
 * Created by ASUS-NB on 2017/2/9.
 */

public class AlterPresenterImpl extends AlterPresenter implements OnCallBackListener{
    AlterModel model;
    AlterView view;

    public AlterPresenterImpl(AlterModel model, AlterView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getUserInfo(int uId) {
        model.getUserInfo(uId,this);
    }

    @Override
    public void changeUserInfo(int uId, String uName, char uGender, float uHeight, float uWeight) {
        model.changeUserInfo(uId,uName,uGender,uHeight,uWeight,this);
    }

    @Override
    public void onSucceed(Object o) {
        Log.e("TAG","onSucceed");
        if(o instanceof UserInfo){
            Log.e("TAG","is right");
            view.showUserInfo((UserInfo)o);
        }else if(o instanceof ChangeInfo){
            view.showChangeUserInfo((ChangeInfo)o);
        }
    }

    @Override
    public void onFailed(Throwable t) {

    }
}
