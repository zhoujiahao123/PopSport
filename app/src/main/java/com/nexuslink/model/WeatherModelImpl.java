package com.nexuslink.model;

import com.nexuslink.config.Constants;
import com.nexuslink.model.data.WeatherInfo;
import com.nexuslink.presenter.WeatherPresenter;
import com.nexuslink.presenter.WeatherPresenterImpl;
import com.nexuslink.util.ApiUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2017/1/17.
 */

public class WeatherModelImpl implements WeatherModel{
    private WeatherPresenter presenter = new WeatherPresenterImpl(this);
    public WeatherModelImpl() {
    }

    @Override
    public void getWeather() {
        ApiUtil.getInstance(Constants.WEATHER_URL)
        .getWeatherInfo("重庆","1e7c4773a758efc6dde40d5d216650e8")
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
            public void onNext(WeatherInfo weatherInfo) {
                presenter.setWeatherInfo(weatherInfo);
            }
        });
    }

    @Override
    public void getError() {

    }
}
