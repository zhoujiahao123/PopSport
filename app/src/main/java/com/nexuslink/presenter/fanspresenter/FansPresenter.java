package com.nexuslink.presenter.fanspresenter;

import com.nexuslink.model.fansmodel.FansModel;
import com.nexuslink.presenter.BasePresenter;
import com.nexuslink.ui.view.FansView;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public abstract class FansPresenter extends BasePresenter<FansModel,FansView> {
    public abstract void getFans(int uId);
}
