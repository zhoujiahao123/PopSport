package com.nexuslink.ui.view;

import com.nexuslink.model.data.FansInfo;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public interface FansView extends BaseView{
    void getFans(int uId);

    void setFans(FansInfo fans);
}
