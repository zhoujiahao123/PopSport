package com.nexuslink.model.personfriendmodel;

import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.FansInfo;
import com.nexuslink.util.ApiUtil;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 猿人 on 2017/4/8.
 */

public class FriendModelImpl implements IFriendModel<List<FansInfo.FansBean>> {

    private Api api = ApiUtil.getInstance(Constants.BASE_URL);


    @Override
    public void getFansInfo(int uId, final CallBackListener<List<FansInfo.FansBean>> listener) {
        api.getFans(uId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FansInfo>() {
                    @Override
                    public void call(FansInfo fansInfo) {
                        if (fansInfo.getCode() == Constants.SUCCESS) {
                            listener.onFinish(fansInfo.getFans());
                        } else {
                            listener.onError(new Exception("请求朋友出错了"));
                        }
                    }
                });
    }
}
