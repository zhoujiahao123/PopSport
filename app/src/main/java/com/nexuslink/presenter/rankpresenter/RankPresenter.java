package com.nexuslink.presenter.rankpresenter;

import com.nexuslink.model.BaseModel;
import com.nexuslink.presenter.BasePresenter;
import com.nexuslink.ui.view.BaseView;

/**
 * Created by ASUS-NB on 2017/4/5.
 */

public abstract class RankPresenter extends BasePresenter<BaseModel,BaseView> {
    public abstract void getRankInfo();
}
