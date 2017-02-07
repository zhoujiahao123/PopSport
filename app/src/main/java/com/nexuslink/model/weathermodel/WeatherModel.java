package com.nexuslink.model.weathermodel;

import com.nexuslink.model.BaseModel;

/**
 * Created by ASUS-NB on 2017/1/17.
 */

public interface WeatherModel extends BaseModel {
    void getWeather(String cityName,onCallBackListener listener);
}
