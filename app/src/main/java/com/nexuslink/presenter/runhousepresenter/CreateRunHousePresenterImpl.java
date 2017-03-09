package com.nexuslink.presenter.runhousepresenter;

import android.text.TextUtils;

import com.nexuslink.app.BaseApplication;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.runhousemodel.CreateRunHouseModel;
import com.nexuslink.model.runhousemodel.CreateRunHouseModelImpl;
import com.nexuslink.ui.view.CreateRunHouseView;
import com.nexuslink.util.ToastUtil;

/**
 * Created by 猿人 on 2017/2/25.
 */

public class CreateRunHousePresenterImpl implements CreateRunHousePresenter {
    /**
     * 持有view和model
     */
    private CreateRunHouseModel runHouseModel;
    private CreateRunHouseView mView;

    public CreateRunHousePresenterImpl(CreateRunHouseView mView) {
        this.mView = mView;
        runHouseModel = new CreateRunHouseModelImpl();
    }

    @Override
    public void createRunHouse() {
        if (mView.getGoal() == 0 ) {
            ToastUtil.showToast(BaseApplication.getContext(),"时间或距离不能为0哦");
        }else if(TextUtils.isEmpty(mView.getRoomName())){
            ToastUtil.showToast(BaseApplication.getContext(),"您还没有输入跑房名字呢");
        }else{
            mView.showProgress();
            runHouseModel.createRunHouse(mView.getType(), mView.getGoal(), mView.getRoomName(), mView.getStartTime(), new CallBackListener() {
                @Override
                public void onFinish(Object o) {
                    int rId = (int) o;
                    mView.hideProgress();
                    mView.showSuccess();
                    mView.insertOneRoom(rId);
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    mView.hideProgress();
                    mView.showError();
                }
            });
        }

    }
}
