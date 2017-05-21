package com.nexuslink.model.runhousemodel;

import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.CreateRunHouseResult;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.UserUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 猿人 on 2017/2/25.
 */

public class CreateRunHouseModelImpl implements CreateRunHouseModel {
    Api api = ApiUtil.getInstance(Constants.BASE_URL);
    @Override
    public void createRunHouse(int type, int goal, String name, String startTime, final CallBackListener listener) {

            api.createRoom(UserUtils.getUserId(),type,goal,name,startTime)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<CreateRunHouseResult>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            listener.onError((Exception) e);
                        }

                        @Override
                        public void onNext(CreateRunHouseResult createRunHouseResult) {
                            if(createRunHouseResult.getCode() == Constants.SUCCESS){
                                listener.onFinish(createRunHouseResult.getRId());
                            }else{
                                listener.onError(new Exception("创建跑房失败"));
                            }
                        }
                    });


    }
}
