package com.nexuslink.util;

import android.util.Log;

import com.nexuslink.config.Api;
import com.nexuslink.config.Constants;

/**
 * Created by ASUS-NB on 2017/1/15.
 */

public class ApiUtil {
    private static Api mInstance ;
    private static Api mWeatherInstance;

    public static Api getInstance(String baseUrl){
        if(mInstance==null){
            mInstance = RetrofitUtil.getInstance(baseUrl).create(Api.class);
            Log.e(Constants.TAG,"getInstance");
        }
        Log.e(Constants.TAG,"getInstance11");
        return mInstance;
    }
    public static Api getWeatherInstance(String baseUrl){
        if(mWeatherInstance==null){
            mWeatherInstance = RetrofitUtil.getRetrofitInstanceFroWeather(baseUrl).create(Api.class);
            Log.e(Constants.TAG,"getInstance");
        }
        Log.e(Constants.TAG,"getInstance11");
        return mWeatherInstance;
    }
}
