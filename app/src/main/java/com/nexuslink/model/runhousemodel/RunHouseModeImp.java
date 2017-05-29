package com.nexuslink.model.runhousemodel;

import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.LoadRoomsResult;
import com.nexuslink.model.data.MyLoadRoomsResult;
import com.nexuslink.model.data.RoomsBean;
import com.nexuslink.util.ApiUtil;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 猿人 on 2017/2/5.
 */

public class RunHouseModeImp implements RunHouseModel<List<RoomsBean>> {

    Api api = ApiUtil.getInstance(Constants.BASE_URL);

    @Override
    public void onRefresh(int startId, final CallBackListener<List<RoomsBean>> listener) {
        api.getRooms(startId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoadRoomsResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError((Exception) e);
                    }

                    @Override
                    public void onNext(LoadRoomsResult loadRoomsResult) {
                        if(loadRoomsResult.getCode() == Constants.SUCCESS){
                            listener.onFinish(loadRoomsResult.getRoom());
                        }else{
                            listener.onError(new Exception("获取跑房信息时出错"));
                        }
                    }
                });
    }

    @Override
    public void getMyRooms(int uId, final CallBackListener<List<RoomsBean>> listener) {
        api.getMyRooms(uId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyLoadRoomsResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError((Exception) e);
                    }

                    @Override
                    public void onNext(MyLoadRoomsResult myLoadRoomsResult) {
                        if(myLoadRoomsResult.getCode() == Constants.SUCCESS || myLoadRoomsResult.getCode()== Constants.FAILED){
                            listener.onFinish(myLoadRoomsResult.getRooms());
                        }
                    }
                });

    }
}


