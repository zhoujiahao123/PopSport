package com.nexuslink.presenter;

import com.nexuslink.model.WeatherModel;
import com.nexuslink.model.WeatherModelImpl;
import com.nexuslink.model.data.WeatherInfo;
import com.nexuslink.ui.activity.LogInActivity;
import com.nexuslink.ui.view.WeatherView;

/**
 * Created by ASUS-NB on 2017/1/17.
 */

public class WeatherPresenterImpl implements WeatherPresenter {
    private WeatherView weatherView;
    private WeatherModel weatherModel;
    public WeatherPresenterImpl(WeatherModel model){

    }

    public WeatherPresenterImpl(WeatherView view) {
        weatherModel = new WeatherModelImpl();
        weatherView = view;
    }
    @Override
    public void setWeatherInfo(WeatherInfo weatherInfo) {
    }
}
