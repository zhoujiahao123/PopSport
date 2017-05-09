package com.nexuslink.presenter.runhousepresenter;

import com.nexuslink.app.BaseApplication;
import com.nexuslink.model.BasePresenterImpl;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.RoomsBean;
import com.nexuslink.model.runhousemodel.RunHouseModeImp;
import com.nexuslink.model.runhousemodel.RunHouseModel;
import com.nexuslink.ui.view.BaseView;
import com.nexuslink.ui.view.RunHouseView;
import com.nexuslink.util.NetUtils;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/25.
 */

public class RunHousePresenterImpl extends BasePresenterImpl<RunHouseView,List<RoomsBean>> implements RunHousePresenter {
    /***
     * 持有view和model
     */
    private RunHouseModel runHouseModel;


    @Override
    public void onRefresh(int startId, final boolean autoRefresh) {
        if (NetUtils.isNetConnected(BaseApplication.mContext)) {
            if (autoRefresh) {
                mView.showProgress();
            }
            runHouseModel.onRefresh(startId, new CallBackListener<List<RoomsBean>>() {
                @Override
                public void onFinish(List<RoomsBean> o) {
                    if (autoRefresh) {
                        mView.hideProgress();
                        mView.showSuccess();
                    }
                    mView.setRunHouseDatas(o);
                }
                @Override
                public void onError(Exception e) {
                    if (autoRefresh) {
                        mView.hideProgress();
                    }

                    mView.showError();
                }
            });
        } else {
            mView.showError();
        }
    }

    @Override
    public void onLoadMore(int startId) {
        runHouseModel.onRefresh(startId, new CallBackListener<List<RoomsBean>>() {
            @Override
            public void onFinish(List<RoomsBean> list) {
                if (list != null && list.size() > 0) {
                    mView.addRunHouse(list);
                    mView.showSuccess();
                } else {
                    mView.showNoMore();
                }
            }
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                mView.showError();
            }
        });
    }

    @Override
    public void getMyRooms(int uId) {
        runHouseModel.getMyRooms(uId,this);
    }

    @Override
    public void onFinish(List<RoomsBean> o) {
        super.onFinish(o);
        mView.setRunHouseDatas(o);
    }

    @Override
    public void onError(Exception e) {
        super.onError(e);
    }

    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        runHouseModel = new RunHouseModeImp();
    }
}
