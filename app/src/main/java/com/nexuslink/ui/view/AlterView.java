package com.nexuslink.ui.view;

import com.nexuslink.model.data.UserInfo;

/**
 * Created by ASUS-NB on 2017/2/9.
 */

public interface AlterView extends BaseView {
    void showUserInfo(UserInfo userInfo);

    void showError();
}
