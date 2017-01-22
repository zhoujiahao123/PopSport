package com.nexuslink.config;



import com.nexuslink.User;
import com.nexuslink.model.data.FollowInfo;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.data.WeatherInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ASUS-NB on 2017/1/15.
 */

public interface Api {
    //获取天气信息
    @GET("query")
    Observable<WeatherInfo> getWeatherInfo(@Query("cityname") String cityName, @Query("key")String key);

    //关注某人
    @GET("friend/follow")
    Observable<FollowInfo> getFollowInfo(@Query("uId") int uId,@Query("fId") int fId);

    //获取已关注的好友
    @GET("friend/mine")
    Observable<FollowedInfo> getFollowedInfo(@Query("uId") int uId);

    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<User> logIn(@Field("uName")String uName,@Field("uPassword")String uPassword);
}
