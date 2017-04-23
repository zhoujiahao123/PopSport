package com.nexuslink.presenter.runhousepresenter;

import com.nexuslink.app.BaseApplication;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.LoadRoomsResult;
import com.nexuslink.model.runhousemodel.RunHouseModeImp;
import com.nexuslink.model.runhousemodel.RunHouseModel;
import com.nexuslink.ui.view.RunHouseView;
import com.sina.weibo.sdk.utils.NetworkHelper;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/25.
 */

public class RunHousePresenterImpl implements RunHousePresenter {
    /***
     * 持有view和model
     */
    private RunHouseView mView;
    private RunHouseModel runHouseModel;

    public RunHousePresenterImpl(RunHouseView mView) {
        this.mView = mView;
        runHouseModel = new RunHouseModeImp();
    }

    @Override
    public void onRefresh(int startId, final boolean autoRefresh) {


        if(NetworkHelper.isNetworkAvailable(BaseApplication.mContext)){
            if(autoRefresh){
                mView.showProgress();
            }

            runHouseModel.onRefresh(startId, new CallBackListener() {
                @Override
                public void onFinish(Object o) {
                    if(autoRefresh){
                        mView.hideProgress();
                        mView.showSuccess();
                    }
                    List<LoadRoomsResult.RoomBean> list = (List<LoadRoomsResult.RoomBean>) o;
                    mView.setRunHouseDatas(list);
                }

                @Override
                public void onError(Exception e) {

                    if(autoRefresh){
                        mView.hideProgress();
                    }

                    mView.showError();
                }
            });
        }else{
            mView.showError();
        }

    }

    @Override
    public void onLoadMore(int startId) {
        runHouseModel.onRefresh(startId, new CallBackListener() {
            @Override
            public void onFinish(Object o) {
                List<LoadRoomsResult.RoomBean> list = (List<LoadRoomsResult.RoomBean>) o;
                if(list != null && list.size()>0){
                    mView.addRunHouse(list);
                    mView.showSuccess();
                }else {
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
}
