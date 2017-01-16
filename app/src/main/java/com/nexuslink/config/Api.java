package com.nexuslink.config;



import com.nexuslink.model.data.WeatherInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ASUS-NB on 2017/1/15.
 */

public interface Api {
    //获取天气信息
    @GET("http://op.juhe.cn/onebox/weather/")
    Observable<WeatherInfo> getWeatherInfo(@Query("cityname") String cityName, @Query("key")String key);
}
