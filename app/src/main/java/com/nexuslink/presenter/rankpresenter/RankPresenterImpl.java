package com.nexuslink.presenter.rankpresenter;

import com.elvishew.xlog.XLog;
import com.nexuslink.model.data.RankInfo;
import com.nexuslink.model.rankmodel.OnRankCallBackLisenter;
import com.nexuslink.model.rankmodel.RankModel;
import com.nexuslink.ui.view.RankView;

/**
 * Created by ASUS-NB on 2017/4/5.
 */

public class RankPresenterImpl extends RankPresenter implements OnRankCallBackLisenter {
    RankModel model;
    RankView view;

    public RankPresenterImpl(RankView view,RankModel model){
        this.model =model;
        this.view = view;
    }
    @Override
    public void getRankInfo() {
        model.getRankInfo(this);
    }

    @Override
    public void onSucceed(Object o) {
        if(o instanceof RankInfo){
            view.showRank((RankInfo)o);
        }
    }

    @Override
    public void onFailed(Throwable t) {
        XLog.e(t.toString());
    }
}
