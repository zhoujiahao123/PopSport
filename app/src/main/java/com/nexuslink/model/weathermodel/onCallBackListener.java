package com.nexuslink.model.weathermodel;

import com.nexuslink.model.data.WeatherInfo;

/**
 * Created by ASUS-NB on 2017/2/5.
 */

public interface onCallBackListener {
    void succeed(WeatherInfo weatherInfo);

    void onfailed(Throwable e);
}
