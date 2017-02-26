package com.nexuslink.model.runhousemodel;

import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.CreateRunHouseResult;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.UserUtils;

import java.util.Date;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 猿人 on 2017/2/25.
 */

public class CreateRunHouseModelImpl implements CreateRunHouseModel {
    Api api = ApiUtil.getInstance(Constants.BASE_URL);
    @Override
    public void createRunHouse(int type, int goal, String name, Date date, final CallBackListener listener) {

            api.createRoom(UserUtils.getUserId(),type,goal,name,date)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<CreateRunHouseResult>() {
                        @Override
                        public void call(CreateRunHouseResult createRunHouseResult) {
                            if(createRunHouseResult.getCode() == Constants.SUCCESS){
                                listener.onFinish(null);
                            }else{
                                listener.onError(new Exception("创建跑房失败"));
                            }
                        }
            });


    }
}
