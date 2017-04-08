package com.nexuslink.presenter.personfriendpresenter;

import com.nexuslink.model.BasePresenterImpl;
import com.nexuslink.model.data.FansInfo;
import com.nexuslink.model.personfriendmodel.IFriendModel;
import com.nexuslink.ui.view.IFansView;

import java.util.List;

/**
 * Created by 猿人 on 2017/4/8.
 */

public class FriendPresenterImpl extends BasePresenterImpl<IFansView, List<FansInfo.FansBean>> implements IFriendPresenter {

    private IFriendModel mFriendModel;

    @Override
    public void onCreate() {
        mFriendModel = new com.nexuslink.model.personfriendmodel.FriendModelImpl();
    }

    @Override
    public void onFinish(List<FansInfo.FansBean> o) {
        super.onFinish(o);
        mView.setDatas(o);
    }

    @Override
    public void getFansInfo(int uId) {
        mFriendModel.getFansInfo(uId,this);
    }

}
