package com.nexuslink.util;

import com.nexuslink.model.data.WeatherInfo;

import retrofit2.Response;

/**
 * Created by ASUS-NB on 2017/1/15.
 */

public interface WeatherCallbackListener {
    void succeed(WeatherInfo response);
    void failed(Throwable t);
}
