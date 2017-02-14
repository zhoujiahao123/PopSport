package com.nexuslink.presenter.alterpresenter;
import android.util.Log;

import com.elvishew.xlog.XLog;
import com.nexuslink.model.altermodel.AlterModel;
import com.nexuslink.model.altermodel.OnCallBackListener;
import com.nexuslink.model.data.ChangeInfo;
import com.nexuslink.model.data.ChangeInfo1;
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
    public void changeUserInfo(int uId,char uGender, float uHeight, float uWeight) {
        model.changeUserInfo(uId,uGender,uHeight,uWeight,this);
    }

    @Override
    public void changeNickName(int uId, String uName) {
        model.changeNickName(uId,uName,this);
    }

    @Override
    public void changePassword(int uId, String oldPassword, String newPassword) {
            model.changePassword(uId,oldPassword,newPassword);
    }

    @Override
    public void onSucceed(Object o) {
        XLog.i("alterPresenter is onSucceed");
        if(o instanceof ChangeInfo1){
            view.showChangeNickName((ChangeInfo1)o);
            XLog.i("修改用户的昵称成功");
        }else if(o instanceof UserInfo){
            XLog.i("展示用户的信息，进入该界面的时候调用的方法成功");
            view.showUserInfo((UserInfo)o);
        }else if(o instanceof ChangeInfo){
            XLog.i("修改用户的基本信息成功");
            view.showChangeUserInfo((ChangeInfo)o);
        }
    }

    @Override
    public void onFailed(Throwable t) {

    }
}
