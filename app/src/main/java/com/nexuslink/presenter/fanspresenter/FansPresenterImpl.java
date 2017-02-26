package com.nexuslink.presenter.fanspresenter;

import com.elvishew.xlog.XLog;
import com.nexuslink.model.data.FansInfo;
import com.nexuslink.model.fansmodel.FansModel;
import com.nexuslink.model.fansmodel.OnCallbackListener;
import com.nexuslink.ui.view.FansView;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public class FansPresenterImpl extends FansPresenter implements OnCallbackListener{
    FansView fansView;
    FansModel fansModel;

    public FansPresenterImpl(FansModel fansModel, FansView fansView) {
        this.fansModel = fansModel;
        this.fansView = fansView;
    }

    @Override
    public void getFans(int uId) {
        fansModel.getFans(uId,this);
    }

    @Override
    public void onSucceed(Object o) {
        if(o instanceof FansInfo){
            fansView.setFans((FansInfo) o);
        }
    }

    @Override
    public void onFailed(Throwable t) {
        XLog.e("获取用户的粉丝的时候出现了错误"+t.toString());
    }
}
