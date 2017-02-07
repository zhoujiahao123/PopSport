package com.nexuslink.presenter.weatherpresenter;

import com.nexuslink.model.BaseModel;
import com.nexuslink.model.data.WeatherInfo;
import com.nexuslink.presenter.BasePresenter;
import com.nexuslink.ui.view.BaseView;

/**
 * Created by ASUS-NB on 2017/1/17.
 */

public abstract class WeatherPresenter extends BasePresenter<BaseModel,BaseView> {
    public abstract void setWeatherInfo(WeatherInfo weatherInfo);

    public abstract void getWeatherInfo(String cityName);
}
