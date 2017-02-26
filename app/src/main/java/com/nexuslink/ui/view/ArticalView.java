package com.nexuslink.ui.view;

import com.nexuslink.model.data.AticalInfo;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public interface ArticalView extends BaseView {
    void getArtical(int uId,int writeId);

    void showArtical(AticalInfo info);
}
