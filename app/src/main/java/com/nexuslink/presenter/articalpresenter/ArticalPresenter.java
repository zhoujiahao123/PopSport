package com.nexuslink.presenter.articalpresenter;

import com.nexuslink.model.articalModel.ArticalModel;
import com.nexuslink.presenter.BasePresenter;
import com.nexuslink.ui.view.ArticalView;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public abstract class ArticalPresenter extends BasePresenter<ArticalModel,ArticalView> {
    public abstract void getArtical(int uId,int writeId);
}
