package com.nexuslink.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.nexuslink.R;
import com.nexuslink.Run;
import com.nexuslink.Steps;
import com.nexuslink.User;
import com.nexuslink.UserDao;
import com.nexuslink.app.BaseActivity;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.GetDistanceResult;
import com.nexuslink.model.data.GetStepResult;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.presenter.loginpresenter.LogInPresenter;
import com.nexuslink.presenter.loginpresenter.LogInPresenterImp;
import com.nexuslink.ui.view.LoginView;
import com.nexuslink.util.ApiUtil;
import com.nexuslink.util.DBUtil;
import com.nexuslink.util.IdUtil;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.UserUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ASUS-NB on 2016/12/17.
 */

public class LogInActivity extends BaseActivity implements LoginView {
    private static final String TAG = "AppCompatActivity";
    @BindView(R.id.text_input_name)
    TextInputEditText textInputName;
    @BindView(R.id.text_input_password)
    TextInputEditText textInputPassword;
    @BindView(R.id.container)
    RelativeLayout container;
    private LogInPresenter presenter;

    //用来判断是否已经登录
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    OkHttpClient okHttpClient = new OkHttpClient();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences= getSharedPreferences("already", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if(sharedPreferences.getInt("already",0)==1){
            startActivity(new Intent(this,MainViewActivity.class));
            finish();
        }else {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_login_2);
            ButterKnife.bind(this);
            container.getBackground().setAlpha(20);
            presenter = new LogInPresenterImp(this);
        }

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(LogInActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
            Intent mainViewIntent = new Intent(LogInActivity.this, MainViewActivity.class);
            startActivity(mainViewIntent);
            finish();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    final AlertDialog dialog = builder.create();

    @Override
    public void succeedLogIn() {

        //进行提示
        final TextView tv = new TextView(this);
        tv.setText("请稍候，我们正在为您做一些初始工作...");
        builder.setView(tv);
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //辅助变量
                    boolean isInsert[] = {false,false,false};

                    //请求用户信息
                    RequestBody body =new FormBody.Builder().add("uId", String.valueOf(IdUtil.getuId())).build();
                    Request request = new Request.Builder().post(body).url(Constants.BASE_URL+"user/getInfo").build();
                    Response response  = okHttpClient.newCall(request).execute();
                    String content = response.body().string();)
                    XLog.e("登录时存储用户数据成功"+content);
                    Gson gson = new Gson();

                    UserInfo userInfo = gson.fromJson(content,UserInfo.class);
                    if(userInfo.getCode() == Constants.SUCCESS){
                        UserDao userDao = BaseApplication.getDaosession().getUserDao();
                        User user = userDao.queryBuilder().where(UserDao.Properties.Already.eq(1)).unique();
                        String achievement=new String();
                        for(int i =0;i<8;i++){
                            achievement+= String.valueOf(userInfo.getUser().getUAchievements()[i]);
                        }
                        user.setUAchievements(achievement.substring(1,achievement.length()-1));
                        user.setUExp(userInfo.getUser().getUExp());
                        user.setUFansNum(userInfo.getUser().getUFansNum());
                        user.setUGender(userInfo.getUser().getUGender());
                        user.setUHeight(userInfo.getUser().getUHeight());
                        user.setUImg(userInfo.getUser().getUImg());
                        user.setUHistoryMileage(Long.valueOf(userInfo.getUser().getUHistoryMileage()));
                        user.setUHistoryStep(Long.valueOf(userInfo.getUser().getUHistoryStep()));
                        user.setUName(userInfo.getUser().getUName());
                        user.setUWeight(userInfo.getUser().getUWeight());
                        userDao.update(user);
                        isInsert[0] = true;
                    }

                    //清除数据
                    DBUtil.getStepsDao().deleteAll();
                    DBUtil.getRunDao().deleteAll();
                    //请求用户历史跑步公里数
                    retrofit2.Response<GetDistanceResult> distanceRes = ApiUtil.getInstance(Constants.BASE_URL).getDistance(UserUtils.getUserId()).execute();
                    if(distanceRes.body().getCode() == Constants.SUCCESS){
                        //进行存储
                       List<GetDistanceResult.RecordBean> recordBeanList =  distanceRes.body().getRecord();
                        //开始存储到本地种
                        List<Run> runlist = new ArrayList<Run>();
                        for(GetDistanceResult.RecordBean recordBean:recordBeanList){
                            Run run = new Run(null,recordBean.getDistance(),recordBean.getAverageSpeed(),recordBean.getPathline(),recordBean.getStartPoint(),recordBean.getEndPoint(),recordBean.getTime().split(" ")[0],
                                    recordBean.getTime().split(" ")[1],true);
                            runlist.add(run);
                        }
                        DBUtil.getRunDao().insertInTx(runlist);
                        isInsert[1] = true;
                    }
                    //进行走步数的存储
                    retrofit2.Response<GetStepResult> stepRes = ApiUtil.getInstance(Constants.BASE_URL).getStep(UserUtils.getUserId()).execute();
                    if(stepRes.body().getCode() == Constants.SUCCESS){
                        //进行存储
                        List<GetStepResult.RecordBean> recordBeanList = stepRes.body().getRecord();
                        List<Steps> stepsList = new ArrayList<Steps>();
                        for(GetStepResult.RecordBean recordBean:recordBeanList){
                            Steps steps = new Steps(null,recordBean.getStep(),recordBean.getDate(),true);
                            stepsList.add(steps);
                        }
                        DBUtil.getStepsDao().insertInTx(stepsList);
                        isInsert[2] = true;
                    }
                    //完成基本工作，进行页面跳转
                    if(isInsert[0] == true && isInsert[1] == true && isInsert[2] == true){
                        handler.sendEmptyMessage(SUCCESS);
                    }else{
                        handler.sendEmptyMessage(FAILED);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    private final int SUCCESS = 1;
    private final int FAILED = 0
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  SUCCESS:
                    editor.putInt("already",1);
                    editor.commit();

                    Intent mainViewIntent = new Intent(LogInActivity.this, MainViewActivity.class);
                    startActivity(mainViewIntent);
                    finish();
                    break;
                case FAILED:
                    ToastUtil.showToast(LogInActivity.this,"登录过程中出现错误，请重试...");
                    break;
            }
            dialog.dismiss();
        }
    };

    @Override
    public void failedLogIn() {
        Snackbar.make(container,"您输入的密码有误",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void logIn(String uName, String password) {
        presenter.logInToService(uName, password);
    }

    @Override
    public void signIn() {
        startActivity(new Intent(this, SignInActivity.class));
    }
    @OnClick({R.id.login, R.id.register, R.id.qq_login, R.id.xinla_log,R.id.forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                if(textInputName.getText().toString().equals("")){
                    Snackbar.make(container,"用户名不能为空",Snackbar.LENGTH_SHORT).show();
                }else if(textInputPassword.getText().toString().equals("")){
                    Snackbar.make(container,"密码不能为空",Snackbar.LENGTH_SHORT).show();
                }else {
                    presenter.logInToService(textInputName.getText().toString(), textInputPassword.getText().toString());
                }
                break;
            case R.id.register:
                signIn();
                break;
            case R.id.qq_login:
                UMShareAPI mShareAPI = UMShareAPI.get(LogInActivity.this);
                mShareAPI.getPlatformInfo(LogInActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.xinla_log:
                UMShareAPI mShareAPI1 = UMShareAPI.get(LogInActivity.this);
                mShareAPI1.getPlatformInfo(LogInActivity.this, SHARE_MEDIA.SINA, umAuthListener);
                break;
            case R.id.forget_password:
                break;
        }
    }
}
