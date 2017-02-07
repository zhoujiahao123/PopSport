package com.nexuslink.presenter.weatherpresenter;

import android.util.Log;

import com.nexuslink.model.data.WeatherInfo;
import com.nexuslink.model.weathermodel.WeatherModel;
import com.nexuslink.model.weathermodel.WeatherModelImpl;
import com.nexuslink.model.weathermodel.onCallBackListener;
import com.nexuslink.ui.view.WeatherView;

/**
 * Created by ASUS-NB on 2017/1/17.
 */

public class WeatherPresenterImpl extends WeatherPresenter implements onCallBackListener {
    private WeatherView weatherView;
    private WeatherModel weatherModel;
    public WeatherPresenterImpl(WeatherView view) {
        weatherModel = new WeatherModelImpl();
        weatherView = view;
    }
    @Override
    public void setWeatherInfo(WeatherInfo weatherInfo) {
        weatherView.showWeather(weatherInfo);
    }

    @Override
    public void getWeatherInfo(String cityName) {
        Log.e("TAG","getWeatherInfo");
        weatherModel.getWeather(cityName,this);

    }

    @Override
    public void succeed(WeatherInfo weatherInfo) {
        setWeatherInfo(weatherInfo);
    }

    @Override
    public void onfailed(Throwable e) {

    }
}
