package com.nexuslink.presenter.runhousepresenter;

import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.runhousemodel.RunHouseModeImp;
import com.nexuslink.model.runhousemodel.RunHouseModel;
import com.nexuslink.ui.view.RunHouseView;

/**
 * Created by 猿人 on 2017/2/5.
 */

public class RunHousePresenter {
    private RunHouseView mRunHouseView;
    private RunHouseModel mRunHouseModel;

    public RunHousePresenter(RunHouseView mRunHouseView) {
        this.mRunHouseView = mRunHouseView;
        mRunHouseModel = new RunHouseModeImp();
    }
    public void loadDetail(long id){
        mRunHouseView.showProgress();
        //开始发起请求
        mRunHouseModel.loadRunHouseDetail(id, new CallBackListener() {
            @Override
            public void onFinish(Object obj) {
                mRunHouseView.hideProgress();
                //得到返回对象后进行传入
                mRunHouseView.intentDetailActivity();
            }

            @Override
            public void onError(Exception e) {
                mRunHouseView.hideProgress();
                mRunHouseView.showError();
            }
        });
    }
}
