package com.nexuslink.model.personfriendmodel;

import com.nexuslink.model.CallBackListener;

/**
 * Created by 猿人 on 2017/4/8.
 */

public interface IFriendModel<T> {
    void getFansInfo(int uId, CallBackListener<T> listener);
}
