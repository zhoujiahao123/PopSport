package com.nexuslink.config;



import com.nexuslink.User;
import com.nexuslink.model.data.CommentInfo;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.model.data.FollowInfo;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.data.FriendInfo;
import com.nexuslink.model.data.UserInfo;
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
    @GET("onebox/weather/query")
    Observable<WeatherInfo> getWeatherInfo(@Query("cityname")String cityname, @Query("key")String key);
    //关注某人
    @GET("friend/follow")
    Observable<FollowInfo> getFollowInfo(@Query("uId") int uId,@Query("fId") int fId);

    //获取已关注的好友
    @GET("friend/mine")
    Observable<FollowedInfo> getFollowedInfo(@Query("uId") int uId);

    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<User> logIn(@Field("uName")String uName, @Field("uPassword")String uPassword);

    //获取好友的个人信息
    @FormUrlEncoded
    @POST("friend/getInfo")
    Observable<FriendInfo> getFriendInfo(@Field("fId")int fId);

    //获取用户个人信息
    @FormUrlEncoded
    @POST("user/getInfo")
    Observable<UserInfo> getUserInfo(@Field("uId")int uId);

    //发表小话题
    //还差图片
    @FormUrlEncoded
    @POST("article/publish")
    Observable<Integer> publishArtice(@Field("uId") int userId, @Field("aText") String text);


    //获取话题内容
    @FormUrlEncoded
    @POST("article/getOne")
    Observable<CommunityInfo> getArticles(@Field("uId") int userId, @Field("aId") int articleId);

    //评论话题
    @FormUrlEncoded
    @POST("article/comment")
    Observable<CommentInfo> postComment(@Field("uId") int userId,@Field("aId") int articleId,@Field("aComment") String comment);

    //查看某个话题的评论
    @FormUrlEncoded
    @POST("article/like")
    Observable<Integer> getComments(@Field("aId") int articleId);

    //为某个话题点赞
    @FormUrlEncoded
    @POST("article/like")
    Observable<Integer> postLike(@Field("uId") int userId,@Field("aId") int articleId);

    //取消点赞
    @FormUrlEncoded
    @POST("article/dislike")
    Observable<Integer> postDisLike(@Field("uId") int userId,@Field("aId") int articleId);
}
