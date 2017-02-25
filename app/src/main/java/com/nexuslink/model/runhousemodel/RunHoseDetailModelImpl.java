package com.nexuslink.model.runhousemodel;

import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.JoinRoomResult;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.UserUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 猿人 on 2017/2/25.
 */

public class RunHoseDetailModelImpl implements RunHouseDetailModel {
    Api api = ApiUtil.getInstance(Constants.BASE_URL);
    @Override
    public void joinRoom(int roomId, final CallBackListener listener) {
        api.joinRoom(UserUtils.getUserId(),roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JoinRoomResult>() {
                    @Override
                    public void call(JoinRoomResult joinRoomResult) {
                        if(joinRoomResult.getCode() == Constants.SUCCESS){
                            listener.onFinish(joinRoomResult.getRoom().getUsers());
                        }else{
                            listener.onError(new Exception("加入跑房出错"));
                        }
                    }
                });
    }
}
