package com.nexuslink.model.loginmodel;


import android.content.Intent;
import android.util.Log;

import com.elvishew.xlog.XLog;
import com.nexuslink.Run;
import com.nexuslink.Steps;
import com.nexuslink.User;
import com.nexuslink.UserDao;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.CallBackListener;
import com.nexuslink.model.data.GetDistanceResult;
import com.nexuslink.model.data.GetStepResult;
import com.nexuslink.model.data.UIdInfo;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.ui.activity.OtherLogInfoAddInfo;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.DBUtil;
import com.nexuslink.util.UserUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS-NB on 2016/12/19.
 */

public class LogInModeImp implements LogInModel {


    private String TAG = "LoginModel";
    private UserDao userDao;

    public LogInModeImp() {
        userDao = DBUtil.getUserDao();
    }
    @Override
    public void getOtherLogInInfo(String openId, String uName, char uGender, File uImg,final CallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .otherLogInfo(55,uName,uGender,null)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<UIdInfo, Observable<UserInfo>>() {
                    @Override
                    public Observable<UserInfo> call(UIdInfo uIdInfo) {
                        XLog.e("第三方登录失败111");
                        if (uIdInfo.getCode() == Constants.SUCCESS && uIdInfo.getuId() != 0) {
                                //清除数据
                                DBUtil.getStepsDao().deleteAll();
                                DBUtil.getRunDao().deleteAll();
                                DBUtil.getUserDao().deleteAll();
                                XLog.e("第三方登录失败222");
                            return ApiUtil.getInstance(Constants.BASE_URL).getUserInfo(uIdInfo.getuId());
                        } else {
                            listener.onError(new Exception("登陆过程出错"));
                            XLog.e("第三方登录失败333");
                            return null;
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<UserInfo, Observable<GetDistanceResult>>() {
                    @Override
                    public Observable<GetDistanceResult> call(UserInfo userInfo) {
                        if(userInfo.getCode() == Constants.SUCCESS){
                            if(userInfo.getUser().getUHeight()==null){
                                Intent intent = new Intent(BaseApplication.getContext(), OtherLogInfoAddInfo.class);
                                BaseApplication.getContext().startActivity(intent);
                                return null;
                            }else {
                                User user = new User();
                                StringBuffer achievement = new StringBuffer();
                                for (Boolean b : userInfo.getUser().getUAchievements()) {
                                    achievement.append(b + ",");
                                }
                                user.setAlready(1);
                                user.setUid(userInfo.getUser().getUid());
                                user.setUAchievements(achievement.substring(0, achievement.length() - 1));
                                user.setUExp(userInfo.getUser().getUExp());
                                user.setUFansNum(userInfo.getUser().getUFansNum());
                                user.setUGender(userInfo.getUser().getUGender());
                                user.setUHeight(userInfo.getUser().getUHeight());
                                user.setUImg(userInfo.getUser().getUImg());
                                user.setUHistoryMileage(Long.valueOf(userInfo.getUser().getUHistoryMileage()));
                                user.setUHistoryStep(Long.valueOf(userInfo.getUser().getUHistoryStep()));
                                user.setUName(userInfo.getUser().getUName());
                                user.setUWeight(userInfo.getUser().getUWeight());
                                userDao.insert(user);
                                Log.i(TAG, (userDao.loadAll() == null ? "yes" : "no"));
                                if (!userDao.loadAll().isEmpty()) {
                                    Log.i(TAG, userDao.loadAll().size() + "");
                                    List<User> list = userDao.loadAll();
                                    Log.i(TAG, list.get(0).getUid() + " " + list.get(0).getAlready());
                                }

                                Log.i(TAG, "插入用户数据成功");
                                return ApiUtil.getInstance(Constants.BASE_URL).getDistance(userInfo.getUser().getUid());
                            }
                        }else {
                            listener.onError(new Exception("登陆过程出错"));
                            return null;
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<GetDistanceResult, Observable<GetStepResult>>() {
                    @Override
                    public Observable<GetStepResult> call(GetDistanceResult getDistanceResult) {
                        Log.i(TAG, "第三次请求,请求跑步信息:" + Thread.currentThread());
                        if (getDistanceResult.getCode() == Constants.SUCCESS) {
                            //开始存储到本地种
                            List<Run> runlist = new ArrayList<Run>();
                            for (GetDistanceResult.RecordBean recordBean : getDistanceResult.getRecord()) {
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
                        return ApiUtil.getInstance(Constants.BASE_URL).getStep(UserUtils.getUserId());
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<GetStepResult>() {
                    @Override
                    public void call(GetStepResult getStepResult) {
                        Log.i(TAG, "第四次请求,请求计步信息:" + Thread.currentThread());
                        if (getStepResult.getCode() == Constants.SUCCESS) {
                            //进行存储
                            List<GetStepResult.RecordBean> recordBeanList = getStepResult.getRecord();
                            List<Steps> stepsList = new ArrayList<Steps>();
                            for (GetStepResult.RecordBean recordBean : recordBeanList) {
                                Steps steps = new Steps(null, recordBean.getStep(), recordBean.getDate(), true);
                                stepsList.add(steps);
                            }
                            if (!stepsList.isEmpty()) {
                                DBUtil.getStepsDao().insertInTx(stepsList);
                            }
                            listener.onFinish(null);
                        } else if(getStepResult.getCode() == Constants.FAILED){
                            listener.onFinish(null);
                        }
                    }
                });
    }
    @Override
    public void getLogInInfo(String uName, String password, final CallBackListener listener) {
        ApiUtil.getInstance(Constants.BASE_URL)
                .logIn(uName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<UIdInfo, Observable<UserInfo>>() {
                    @Override
                    public Observable<UserInfo> call(UIdInfo uIdInfo) {
                        Log.i(TAG, "第一次请求，登录请求:" + Thread.currentThread());
                        if (uIdInfo.getCode() == Constants.SUCCESS && uIdInfo.getuId() != 0) {
                            //清除数据
                            DBUtil.getStepsDao().deleteAll();
                            DBUtil.getRunDao().deleteAll();
                            DBUtil.getUserDao().deleteAll();

                            return ApiUtil.getInstance(Constants.BASE_URL).getUserInfo(uIdInfo.getuId());
                        } else {
                            listener.onError(new Exception("登陆过程出错"));
                            return null;
                        }

                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<UserInfo, Observable<GetDistanceResult>>() {
                    @Override
                    public Observable<GetDistanceResult> call(UserInfo userInfo) {
                        Log.i(TAG, "第二次请求,请求用户信息:" + Thread.currentThread());
                        if (userInfo.getCode() == Constants.SUCCESS) {
                            User user = new User();
                            StringBuffer achievement = new StringBuffer();
                            for (Boolean b : userInfo.getUser().getUAchievements()) {
                                achievement.append(b + ",");
                            }
                            user.setAlready(1);
                            user.setUid(userInfo.getUser().getUid());
                            user.setUAchievements(achievement.substring(0, achievement.length() - 1));
                            user.setUExp(userInfo.getUser().getUExp());
                            user.setUFansNum(userInfo.getUser().getUFansNum());
                            user.setUGender(userInfo.getUser().getUGender());
                            user.setUHeight(userInfo.getUser().getUHeight());
                            user.setUImg(userInfo.getUser().getUImg());
                            user.setUHistoryMileage(Long.valueOf(userInfo.getUser().getUHistoryMileage()));
                            user.setUHistoryStep(Long.valueOf(userInfo.getUser().getUHistoryStep()));
                            user.setUName(userInfo.getUser().getUName());
                            user.setUWeight(userInfo.getUser().getUWeight());
                            userDao.insert(user);
                            Log.i(TAG, (userDao.loadAll() == null ? "yes" : "no"));
                            if (!userDao.loadAll().isEmpty()) {
                                Log.i(TAG, userDao.loadAll().size() + "");
                                List<User> list = userDao.loadAll();
                                Log.i(TAG, list.get(0).getUid() + " " + list.get(0).getAlready());
                            }

                            Log.i(TAG, "插入用户数据成功");
                            return ApiUtil.getInstance(Constants.BASE_URL).getDistance(userInfo.getUser().getUid());
                        } else {
                            listener.onError(new Exception("登陆过程出错"));
                            return null;
                        }
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<GetDistanceResult, Observable<GetStepResult>>() {
                    @Override
                    public Observable<GetStepResult> call(GetDistanceResult getDistanceResult) {
                        Log.i(TAG, "第三次请求,请求跑步信息:" + Thread.currentThread());
                        if (getDistanceResult.getCode() == Constants.SUCCESS) {
                            //开始存储到本地种
                            List<Run> runlist = new ArrayList<Run>();
                            for (GetDistanceResult.RecordBean recordBean : getDistanceResult.getRecord()) {
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
                        return ApiUtil.getInstance(Constants.BASE_URL).getStep(UserUtils.getUserId());
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Action1<GetStepResult>() {
                    @Override
                    public void call(GetStepResult getStepResult) {
                        Log.i(TAG, "第四次请求,请求计步信息:" + Thread.currentThread());
                        if (getStepResult.getCode() == Constants.SUCCESS) {
                            //进行存储
                            List<GetStepResult.RecordBean> recordBeanList = getStepResult.getRecord();
                            List<Steps> stepsList = new ArrayList<Steps>();
                            for (GetStepResult.RecordBean recordBean : recordBeanList) {
                                Steps steps = new Steps(null, recordBean.getStep(), recordBean.getDate(), true);
                                stepsList.add(steps);
                            }
                            if (!stepsList.isEmpty()) {
                                DBUtil.getStepsDao().insertInTx(stepsList);
                            }
                            listener.onFinish(null);
                        } else if(getStepResult.getCode() == Constants.FAILED){
                            listener.onFinish(null);
                        }
                    }
                });
    }


}
