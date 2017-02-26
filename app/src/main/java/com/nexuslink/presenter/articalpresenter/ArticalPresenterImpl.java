package com.nexuslink.presenter.articalpresenter;

import com.elvishew.xlog.XLog;
import com.nexuslink.model.articalModel.ArticalModel;
import com.nexuslink.model.articalModel.OnCallBackListener;
import com.nexuslink.model.data.AticalInfo;
import com.nexuslink.ui.view.ArticalView;

/**
 * Created by ASUS-NB on 2017/2/25.
 */

public class ArticalPresenterImpl extends ArticalPresenter implements OnCallBackListener{
    ArticalView view;
    ArticalModel model;
    public ArticalPresenterImpl(ArticalModel model, ArticalView view) {
        this.model = model;
        this.view=view;
    }

    @Override
    public void getArtical(int uId, int writeId) {
        model.getArtical(uId,writeId,this);
        XLog.e("正在获取文章");
    }

    @Override
    public void onSucceed(Object o) {
        if(o instanceof AticalInfo){
            view.showArtical((AticalInfo)o);
            XLog.e("获取文章");
        }
    }

    @Override
    public void onFailed(Throwable t) {
        XLog.e("获取好友的文章出现了问题"+t.toString());
    }
}
