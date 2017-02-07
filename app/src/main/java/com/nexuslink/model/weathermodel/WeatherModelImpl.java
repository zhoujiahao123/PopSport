package com.nexuslink.model.weathermodel;

import android.util.Log;

import com.nexuslink.config.Constants;
import com.nexuslink.model.data.WeatherInfo;
import com.nexuslink.util.ApiUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/1/17.
 */

public class WeatherModelImpl implements WeatherModel {
    public WeatherModelImpl() {

    }

    @Override
    public void getWeather(String cityName, final onCallBackListener listener) {
        Log.e("TAG","getWeather");
        ApiUtil.getWeatherInstance(Constants.WEATHER_BASE_URL)
                .getWeatherInfo("重庆","1e7c4773a758efc6dde40d5d216650e8")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onfailed(e);
                        Log.e("TAG",e.toString());
                    }

                    @Override
                    public void onNext(WeatherInfo weatherInfo) {
                        listener.succeed(weatherInfo);
                    }
                });
    }

    @Override
    public void getError() {

    }
}
