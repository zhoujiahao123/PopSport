package com.nexuslink.model.loginmodel;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.elvishew.xlog.XLog;
import com.nexuslink.UserDao;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.ui.activity.OtherLogInfoAddInfo;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.DBUtil;
import com.google.gson.Gson;
import com.nexuslink.Run;
import com.nexuslink.Steps;
import com.nexuslink.User;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.GetDistanceResult;
import com.nexuslink.model.data.GetStepResult;
import com.nexuslink.model.data.UIdInfo;
import com.nexuslink.util.IdUtil;
import com.nexuslink.util.UserUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2016/12/19.
 */

public class LogInModeImp implements LogInModel {

    private OkHttpClient okHttpClient = new OkHttpClient();
    private String TAG = "LoginModel";
    private UserDao userDao;

    public LogInModeImp() {
        userDao = DBUtil.getUserDao();
    }
    @Override
    public void getOtherLogInInfo(String openId, String uName, char uGender, File uImg, final CallBackListener listener) {
        String type = "image/png";
        File file = new File(Environment.getExternalStorageDirectory()+"/poplog.png");
        int random = (int) (Math.random()*100+1);
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("openId", String.valueOf(random))
                .addFormDataPart("uName",uName)
                .addFormDataPart("uGender",String.valueOf(uGender))
                .addFormDataPart("uImg", "uImg", RequestBody.create(MediaType.parse(type), file)).build();
        Request request = new Request.Builder().post(body).url("http://120.77.87.78:8080/arti-sports/api/user/qqLogin").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                Gson gson = new Gson();
                final UIdInfo uIdInfo = gson.fromJson(data,UIdInfo.class);
                ApiUtil.getInstance(Constants.BASE_URL)
                        .getUserInfo(uIdInfo.getuId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<UserInfo>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(UserInfo userInfo) {
                                if(userInfo.getCode()!=500){
                                    if(userInfo.getUser().getUWeight()==0&&userInfo.getUser().getUHeight()==0){
                                        Intent intent= new Intent(BaseApplication.getContext(),OtherLogInfoAddInfo.class);
                                        User user = new User();
                                        StringBuffer achievement = new StringBuffer();
                                        for (Boolean b : userInfo.getUser().getUAchievements()) {
                                            achievement.append(b + ",");
                                        }
                                        user.setAlready(1);
                                        user.setUid(uIdInfo.getuId());
                                        user.setUAchievements(achievement.substring(0, achievement.length() - 1));
                                        user.setUExp(userInfo.getUser().getUExp());
                                        user.setUFansNum(userInfo.getUser().getUFansnum());
                                        user.setUGender(userInfo.getUser().getUGender());
                                        user.setUHeight(userInfo.getUser().getUHeight());
                                        user.setUImg(userInfo.getUser().getUImg());
                                        user.setUHistoryMileage(Long.valueOf(userInfo.getUser().getUHistoryMileage()));
                                        user.setUHistoryStep(Long.valueOf(userInfo.getUser().getUHistoryStep()));
                                        user.setUName(userInfo.getUser().getUName());
                                        user.setUWeight(userInfo.getUser().getUWeight());
                                        userDao.insert(user);
                                        intent.putExtra("uId",uIdInfo.getuId());
                                        BaseApplication.getContext().startActivity(intent);
                                    }else {
                                        User user = new User();
                                        StringBuffer achievement = new StringBuffer();
                                        for (Boolean b : userInfo.getUser().getUAchievements()) {
                                            achievement.append(b + ",");
                                        }
                                        user.setAlready(1);
                                        user.setUid(uIdInfo.getuId());
                                        user.setUAchievements(achievement.substring(0, achievement.length() - 1));
                                        user.setUExp(userInfo.getUser().getUExp());
                                        user.setUFansNum(userInfo.getUser().getUFansnum());
                                        user.setUGender(userInfo.getUser().getUGender());
                                        user.setUHeight(userInfo.getUser().getUHeight());
                                        user.setUImg(userInfo.getUser().getUImg());
                                        user.setUHistoryMileage(Long.valueOf(userInfo.getUser().getUHistoryMileage()));
                                        user.setUHistoryStep(Long.valueOf(userInfo.getUser().getUHistoryStep()));
                                        user.setUName(userInfo.getUser().getUName());
                                        user.setUWeight(userInfo.getUser().getUWeight());
                                        userDao.insert(user);
                                        //第三次请求请求用户计步数
                                        Gson gson1 = new Gson();
                                        RequestBody body3 = new FormBody.Builder()
                                                .add("uId", String.valueOf(uIdInfo.getuId())).build();
                                        Request stepsRequest = new Request.Builder()
                                                .url(Constants.BASE_URL + "sport/getStep")
                                                .post(body3)
                                                .build();
                                        Response response3 = null;
                                        try {
                                            response3 = okHttpClient.newCall(stepsRequest).execute();
                                            GetStepResult stepResult = gson1.fromJson(response3.body().string(), GetStepResult.class);
                                            response3.close();
                                            if (stepResult.getCode() == Constants.SUCCESS || stepResult.getCode() == Constants.FAILED) {
                                                //进行存储
                                                List<GetStepResult.RecordBean> recordBeanList = stepResult.getRecord();
                                                List<Steps> stepsList = new ArrayList<Steps>();
                                                for (GetStepResult.RecordBean recordBean : recordBeanList) {
                                                    Steps steps = new Steps(null, recordBean.getStep(), recordBean.getDate(), true);
                                                    stepsList.add(steps);
                                                }
                                                if (!stepsList.isEmpty()) {
                                                    DBUtil.getStepsDao().insertInTx(stepsList);
                                                }
                                            }

                                            //第四次请求，请求跑步公里数信息
                                            RequestBody body4 = new FormBody.Builder()
                                                    .add("uId", String.valueOf(uIdInfo.getuId())).build();
                                            Request runRecordRequest = new Request.Builder()
                                                    .url(Constants.BASE_URL + "sport/getDistance")
                                                    .post(body4)
                                                    .build();
                                            Response response4 = okHttpClient.newCall(runRecordRequest).execute();
                                            GetDistanceResult distanceResult = gson1.fromJson(response4.body().string(), GetDistanceResult.class);
                                            response4.close();
                                            if (distanceResult.getCode() == Constants.SUCCESS ) {
                                                //开始存储到本地种
                                                List<Run> runlist = new ArrayList<Run>();
                                                for (GetDistanceResult.RecordBean recordBean : distanceResult.getRecord()) {
                                                    Run run = new Run(null, String.valueOf(recordBean.getDistance()),
                                                            String.valueOf(recordBean.getDuration()), String.valueOf(recordBean.getAverageSpeed())
                                                            , recordBean.getPathline(), recordBean.getStartPoint(),
                                                            recordBean.getEndPoint(), recordBean.getDate(),
                                                            recordBean.getTime(), null, true);
                                                    runlist.add(run);
                                                }
                                                if (!runlist.isEmpty()) {
                                                    DBUtil.getRunDao().insertInTx(runlist);
                                                }
                                            }listener.onFinish(null);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }
                            }
                        });
            }
        });
    }
    @Override
    public void getLogInInfo(final String uName, final String password, final CallBackListener listener) {

        //改用okhttpp进行网络请求
//        第一次进行登录
        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        final Gson gson = new Gson();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestBody body1 = new FormBody.Builder()
                            .add("uName",uName)
                            .add("uPassword",password).build();
                    Request loginRequest = new Request.Builder()
                            .url(Constants.BASE_URL + "user/login")
                            .post(body1)
                            .build();
                    Response response1 = client.newCall(loginRequest).execute();
                    UIdInfo uIdInfo = gson.fromJson(response1.body().string(), UIdInfo.class);
                    response1.close();
                    if(uIdInfo.getCode() == Constants.FAILED){
                        listener.onError(new Exception("登录过程出错"));
                        return;
                    }
                    if(uIdInfo.getuId() == 0){
                        listener.onError(new Exception("密码错误"));
                    }
//                    第二次请求用户信息
                    RequestBody body2 = new FormBody.Builder()
                            .add("uId", String.valueOf(uIdInfo.getuId())).build();
                    Request userInfoRequest = new Request.Builder()
                            .url(Constants.BASE_URL + "user/getInfo")
                            .post(body2)
                            .build();
                    Response response2 = client.newCall(userInfoRequest).execute();
                    UserInfo userInfo = gson.fromJson(response2.body().string(), UserInfo.class);
                    response2.close();
                    if (userInfo.getCode() != Constants.SUCCESS) {
                        listener.onError(new Exception("登录过程出错"));
                    }
                    User user = new User();
                    StringBuffer achievement = new StringBuffer();
                    for (Boolean b : userInfo.getUser().getUAchievements()) {
                        achievement.append(b + ",");
                    }
                    user.setAlready(1);
                    user.setUid(userInfo.getUser().getUid());
                    user.setUAchievements(achievement.substring(0, achievement.length() - 1));
                    user.setUExp(userInfo.getUser().getUExp());
                    user.setUFansNum(userInfo.getUser().getUFansnum());
                    user.setUGender(userInfo.getUser().getUGender());
                    user.setUHeight(userInfo.getUser().getUHeight());
                    user.setUImg(userInfo.getUser().getUImg());
                    user.setUHistoryMileage(Long.valueOf(userInfo.getUser().getUHistoryMileage()));
                    user.setUHistoryStep(Long.valueOf(userInfo.getUser().getUHistoryStep()));
                    user.setUName(userInfo.getUser().getUName());
                    user.setUWeight(userInfo.getUser().getUWeight());
                    userDao.insert(user);

                    //第三次请求请求用户计步数
                    RequestBody body3 = new FormBody.Builder()
                            .add("uId", String.valueOf(uIdInfo.getuId())).build();
                    Request stepsRequest = new Request.Builder()
                            .url(Constants.BASE_URL + "sport/getStep")
                            .post(body3)
                            .build();
                    Response response3 = client.newCall(stepsRequest).execute();
                    GetStepResult stepResult = gson.fromJson(response3.body().string(), GetStepResult.class);
                    response3.close();
                    if (stepResult.getCode() == Constants.SUCCESS || stepResult.getCode() == Constants.FAILED) {
                        //进行存储
                        List<GetStepResult.RecordBean> recordBeanList = stepResult.getRecord();
                        List<Steps> stepsList = new ArrayList<Steps>();
                        for (GetStepResult.RecordBean recordBean : recordBeanList) {
                            Steps steps = new Steps(null, recordBean.getStep(), recordBean.getDate(), true);
                            stepsList.add(steps);
                        }
                        if (!stepsList.isEmpty()) {
                            DBUtil.getStepsDao().insertInTx(stepsList);
                        }
                    }

                    //第四次请求，请求跑步公里数信息
                    RequestBody body4 = new FormBody.Builder()
                            .add("uId", String.valueOf(uIdInfo.getuId())).build();
                    Request runRecordRequest = new Request.Builder()
                            .url(Constants.BASE_URL + "sport/getDistance")
                            .post(body4)
                            .build();
                    Response response4 = client.newCall(runRecordRequest).execute();
                    GetDistanceResult distanceResult = gson.fromJson(response4.body().string(), GetDistanceResult.class);
                    response4.close();
                    if (distanceResult.getCode() == Constants.SUCCESS ) {
                        //开始存储到本地种
                        List<Run> runlist = new ArrayList<Run>();
                        for (GetDistanceResult.RecordBean recordBean : distanceResult.getRecord()) {
                            Run run = new Run(null, String.valueOf(recordBean.getDistance()),
                                    String.valueOf(recordBean.getDuration()), String.valueOf(recordBean.getAverageSpeed())
                                    , recordBean.getPathline(), recordBean.getStartPoint(),
                                    recordBean.getEndPoint(), recordBean.getDate(),
                                    recordBean.getTime(), null, true);
                            runlist.add(run);
                        }
                        if (!runlist.isEmpty()) {
                            DBUtil.getRunDao().insertInTx(runlist);
                        }
                    }
                    listener.onFinish(null);
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onError(e);
                }
            }
        }).start();

    }


}
