package com.nexuslink.model.rankmodel;

import com.nexuslink.config.Constants;
import com.nexuslink.model.data.RankInfo;
import com.nexuslink.util.ApiUtil;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/4/5.
 */

public class RankModelImpl implements RankModel {
    @Override
    public void getError() {

    }

    @Override
    public void getRankInfo(final OnRankCallBackLisenter lisenter) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .getRankInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RankInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        lisenter.onFailed(e);
                    }

                    @Override
                    public void onNext(RankInfo rankInfo) {
                        lisenter.onSucceed(rankInfo);
                    }
                });
    }
}
