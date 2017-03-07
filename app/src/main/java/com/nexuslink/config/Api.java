package com.nexuslink.config;


import com.nexuslink.model.data.AticalInfo;
import com.nexuslink.model.data.ChangeInfo;
import com.nexuslink.model.data.ChangeInfo1;
import com.nexuslink.model.data.ChangeInfoPassword;
import com.nexuslink.model.data.CommentInfo;
import com.nexuslink.model.data.CommentResult;
import com.nexuslink.model.data.CommunityInfo;
import com.nexuslink.model.data.CreateRunHouseResult;
import com.nexuslink.model.data.FansInfo;
import com.nexuslink.model.data.FollowInfo;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.data.FriendInfo;
import com.nexuslink.model.data.JoinRoomResult;
import com.nexuslink.model.data.LoadRoomsResult;
import com.nexuslink.model.data.PostLikeResult;
import com.nexuslink.model.data.PublishImagesResult;
import com.nexuslink.model.data.QuiteRoomResult;
import com.nexuslink.model.data.SearchInfo;
import com.nexuslink.model.data.SingleCommunityInfo;
import com.nexuslink.model.data.TaskFlag;
import com.nexuslink.model.data.UIdInfo;
import com.nexuslink.model.data.UpLoadUserImageResult;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.model.data.WeatherInfo;
import com.nexuslink.model.data.WriteArticleResult;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @FormUrlEncoded
    @POST("friend/follow")
    Observable<FollowInfo> getFollowInfo(@Field("uId") int uId,@Field("fId") int fId);

    //获取已关注的好友
    @FormUrlEncoded
    @POST("friend/mine")
    Observable<FollowedInfo> getFollowedInfo(@Field("uId") int uId);

    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<UIdInfo> logIn(@Field("uName")String uName, @Field("uPassword")String uPassword);

    //获取好友的个人信息
    @FormUrlEncoded
    @POST("friend/getInfo")
    Observable<FriendInfo> getFriendInfo(@Field("fId")int fId);

    //获取用户个人信息
    @FormUrlEncoded
    @POST("user/getInfo")
    Observable<UserInfo> getUserInfo(@Field("uId")int uId);

    //更改头像
    @Multipart
    @POST("img/changeImg")
    Observable<UpLoadUserImageResult> changUserImage(@Query("uId") int uerId, @Part("uImg\"; filename=\"userImage.jpg\"") RequestBody file);


    //发表小话题
    @FormUrlEncoded
    @POST("article/publish")
    Observable<WriteArticleResult> publishArtice(@Field("uId") int userId, @Field("aText") String text);

    //话题图片上传
    @Multipart
    @POST("img/articleImg")
    Observable<PublishImagesResult> publishImages(@Query("uId") int uId,@Query("aId") int aId,@Part() List<MultipartBody.Part> body);


    //获取话题单一内容
    @FormUrlEncoded
    @POST("article/getOne")
    Observable<SingleCommunityInfo> getArticle(@Field("uId") int userId, @Field("aId") int articleId);

    //获取多话题内容
    @FormUrlEncoded
    @POST("article/getAll")
    Observable<CommunityInfo> getArticles(@Field("uId") int userId,@Field("aId") int articleId);

    //评论话题
    @FormUrlEncoded
    @POST("article/comment")
    Observable<CommentResult> postComment(@Field("uId") int userId, @Field("aId") int articleId, @Field("aComment") String comment);

    //查看某个话题的评论
    @FormUrlEncoded
    @POST("article/getComment")
    Observable<CommentInfo> getComment(@Field("aId") int articleId);

    //为某个话题点赞
    @FormUrlEncoded
    @POST("article/like")
    Observable<PostLikeResult> postLike(@Field("uId") int userId, @Field("aId") int articleId);

    //取消点赞
    @FormUrlEncoded
    @POST("article/dislike")
    Observable<Integer> postDisLike(@Field("uId") int userId,@Field("aId") int articleId);

    //修改用户的个人信息
    @FormUrlEncoded
    @POST("user/changeInfo")
    Observable<ChangeInfo> changeUserInfo(@Field("uId") int uId, @Field("uGender")char uGender, @Field("uHeight")int
            uHeight, @Field("uWeight")int uWeight);

    //修改用户的头像
    @FormUrlEncoded
    @POST("user/cahngeName")
    Observable<ChangeInfo1> changeNickName(@Field("uId")int uId, @Field("uName")String uName);

    //搜索用户
    @FormUrlEncoded
    @POST("friend/search")
    Observable<SearchInfo> searchUser(@Field("type")int type, @Field("keyword")String keyword);

    //修改密码
    @FormUrlEncoded
    @POST("user/changePassword")
    Observable<ChangeInfoPassword> changePassword(@Field("uId")int uId, @Field("uOldPassword")String uOldPassword, @Field("uNewPassword")String uNewPassword);

    //提交任务
    @FormUrlEncoded
    @POST("user/task")
    Observable<TaskFlag> upLoadTask(@Field("uId")int uId, @Field("type")int type, @Field("taskNum")int taskNum);

    //注册新账号
    @FormUrlEncoded
    @POST("user/register")
    Observable<UIdInfo> requestRegister(@Field("uName")String uName,@Field("uPassword")String uPassword,@Field("uGender")char uGender,@Field("uHeight")
                                        int uHeight,@Field("uWeight")int uWeight);


    //获取用户的粉丝
    @FormUrlEncoded
    @POST("friend/fans")
    Observable<FansInfo> getFans(@Field("uId")int uId);

    //获取用户的话题
    @FormUrlEncoded
    @POST("article/getHis")
    Observable<AticalInfo> getAtical(@Field("uId")int uId,@Field("writerId")int  writeId);

    /**
     * 跑房相关
     */
    //创建跑房
    @FormUrlEncoded
    @POST("room/createRoom")
    Observable<CreateRunHouseResult> createRoom(@Field("uId") int uId, @Field("type") int type, @Field("goal")
            int goal, @Field("roomName") String room, @Field("startTime")String startTime);

    //查看所有房间
    @FormUrlEncoded
    @POST("room/getRooms")
    Observable<LoadRoomsResult> getRooms(@Field("startId") int startId);

    //加入跑房
    @FormUrlEncoded
    @POST("room/join")
    Observable<JoinRoomResult> joinRoom(@Field("uId") int uId,@Field("rId") int rId);

    //退出跑房
    @FormUrlEncoded
    @POST("room/quit")
    Observable<QuiteRoomResult> quitRoom(@Field("uId") int uId, @Field("rId") int rId);





}
