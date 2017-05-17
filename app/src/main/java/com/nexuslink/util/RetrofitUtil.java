package com.nexuslink.util;

import com.elvishew.xlog.XLog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS-NB on 2017/1/15.
 */

public class RetrofitUtil {
    private static Retrofit mInstance;
    private static Retrofit mWeatherInstance;
    private static Retrofit instance;

    private final long DEFAULT_TIMEOUT = 5000;

    /**
     * 单例化
     */
    private RetrofitUtil (String baseUrl){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT,TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT,TimeUnit.MILLISECONDS).build();
        instance = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }
    public static Retrofit getInstance(String baseUrl){
        if(mInstance == null){
            new RetrofitUtil(baseUrl);
        }
        return instance;
    }

    public static Retrofit getRetrofitInstance(String baseUrl){
        if(mInstance==null){
            mInstance = new Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        mInstance = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        XLog.e(mInstance);
        return mInstance;
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
