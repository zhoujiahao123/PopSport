package com.nexuslink.model.runhousemodel;

import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.LoadRoomsResult;
import com.nexuslink.util.ApiUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 猿人 on 2017/2/5.
 */

public class RunHouseModeImp implements RunHouseModel {

    Api api = ApiUtil.getInstance(Constants.BASE_URL);

    @Override
    public void onRefresh(int startId, final CallBackListener listener) {
        api.getRooms(startId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LoadRoomsResult>() {
                    @Override
                    public void call(LoadRoomsResult loadRoomsResult) {
                        if(loadRoomsResult.getCode() == Constants.SUCCESS){
                            listener.onFinish(loadRoomsResult.getRoom());
                        }else{
                            listener.onError(new Exception("获取跑房信息时出错"));
                        }
                    }
                });
    }
}
