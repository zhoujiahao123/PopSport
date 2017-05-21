package com.nexuslink.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS-NB on 2017/1/15.
 */

public class RetrofitUtil {

    private static Retrofit mWeatherInstance;
    private static Retrofit instance;

    private final long DEFAULT_TIMEOUT = 5;

    /**
     * 单例化
     */
    private  RetrofitUtil (String baseUrl){
       final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS).build();
        instance = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }
    public synchronized static Retrofit getInstance(String baseUrl){
        if(instance == null){
            new RetrofitUtil(baseUrl);
        }
        return instance;
    }

    public static Retrofit getRetrofitInstanceFroWeather(String baseUrl){
        if(mWeatherInstance==null){
            mWeatherInstance = new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return mWeatherInstance;
    }

}
