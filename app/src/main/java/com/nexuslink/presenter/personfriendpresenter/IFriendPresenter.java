package com.nexuslink.presenter.personfriendpresenter;

import com.nexuslink.presenter.IBasePresenter;

/**
 * Created by 猿人 on 2017/4/8.
 */

public interface IFriendPresenter extends IBasePresenter {
    void getFansInfo(int uId);
    void getFriendInfo(int uId);
}
