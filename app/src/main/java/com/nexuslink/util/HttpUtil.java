package com.nexuslink.util;

import android.util.Log;

import com.nexuslink.model.data.WeatherInfo;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/1/15.
 */

public class HttpUtil {
    public static void getWeatherInfoUtil(final WeatherCallbackListener listener){
        ApiUtil.getInstance("http://op.juhe.cn/onebox/weather/").getWeatherInfo("重庆","1e7c4773a758efc6dde40d5d216650e8")
        .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WeatherInfo newsDate) {
                     Log.e("TAG",   newsDate.getResult().getData().getRealtime().getMoon());
                    }
                });
    }

}
