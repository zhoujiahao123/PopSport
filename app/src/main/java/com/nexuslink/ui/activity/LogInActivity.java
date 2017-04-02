package com.nexuslink.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.nexuslink.R;
import com.nexuslink.User;
import com.nexuslink.UserDao;
import com.nexuslink.app.BaseActivity;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.presenter.loginpresenter.LogInPresenter;
import com.nexuslink.presenter.loginpresenter.LogInPresenterImp;
import com.nexuslink.ui.view.LoginView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.IdUtil;
import com.nexuslink.util.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.friends.Wechat;
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
    @BindView(R.id.user_image)
    CircleImageView mUserImage;
    @BindView(R.id.login)
    Button mLoginBt;
    @BindView(R.id.register)
    TextView mRegisterTv;
    @BindView(R.id.forget_password)
    TextView mForgetPasswordTv;
    @BindView(R.id.qq_login)
    CircleImageView qqLogin;
    @BindView(R.id.wechat_login)
    CircleImageView wechatLogin;
    @BindView(R.id.xinlang_login)
    CircleImageView xinlangLogin;
    @BindView(R.id.account_input)
    EditText accountInput;
    @BindView(R.id.password_input)
    EditText passwordInput;
    //    @BindView(R.id.text_input_name)
//    TextInputEditText textInputName;
//    @BindView(R.id.text_input_password)
//    TextInputEditText textInputPassword;
//    @BindView(R.id.container)
//    RelativeLayout container;
    private LogInPresenter presenter;

    //用来判断是否已经登录
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    OkHttpClient okHttpClient = new OkHttpClient();

    //===============================================handler
    private final int SUCCESS = 1;
    private final int FAILED = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    editor.putInt("already", 1);
                    editor.commit();

                    Intent mainViewIntent = new Intent(LogInActivity.this, MainViewActivity.class);
                    startActivity(mainViewIntent);
                    //登陆成功发送广播,通知计步更新
                    sendBroadcast(new Intent("ANewacount"));
                    finish();
                    break;
                case FAILED:
                    ToastUtil.showToast(LogInActivity.this, "登录过程中出现错误，请重试...");
                    break;
            }
            dialog.dismiss();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("already", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
//
        if (sharedPreferences.getInt("already", 0) == 1) {
            startActivity(new Intent(this, MainViewActivity.class));
            finish();
        } else {
//            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);

            presenter = new LogInPresenterImp(this);
        }

    }





    AlertDialog.Builder builder;
    AlertDialog dialog;

    @Override
    public void succeedLogIn() {

        //进行提示
        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        final TextView tv = new TextView(this);
        tv.setText("请稍候，我们正在为您做一些初始工作...");
        builder.setView(tv);
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //辅助变量
                    boolean isInsert[] = {false, false, false};

                    //请求用户信息
                    RequestBody body = new FormBody.Builder().add("uId", String.valueOf(IdUtil.getuId())).build();
                    Request request = new Request.Builder().post(body).url(Constants.BASE_URL + "user/getInfo").build();
                    Response response = okHttpClient.newCall(request).execute();
                    String content = response.body().string();
                    XLog.e("登录时存储用户数据成功" + content);
                    Gson gson = new Gson();

                    UserInfo userInfo = gson.fromJson(content, UserInfo.class);
                    if (userInfo.getCode() == Constants.SUCCESS) {
                        UserDao userDao = BaseApplication.getDaosession().getUserDao();
                        User user = userDao.queryBuilder().where(UserDao.Properties.Already.eq(1)).unique();
                        String achievement = new String();
                        for (int i = 0; i < 8; i++) {
                            achievement += String.valueOf(userInfo.getUser().getUAchievements()[i]);
                        }
                        user.setUAchievements(achievement.substring(1, achievement.length() - 1));
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

//                    //清除数据
//                    DBUtil.getStepsDao().deleteAll();
//                    DBUtil.getRunDao().deleteAll();
//                    //请求用户历史跑步公里数
//                    retrofit2.Response<GetDistanceResult> distanceRes = ApiUtil.getInstance(Constants.BASE_URL).getDistance(UserUtils.getUserId()).execute();
//                    if(distanceRes.body().getCode() == Constants.SUCCESS){
//                        //进行存储
//                       List<GetDistanceResult.RecordBean> recordBeanList =  distanceRes.body().getRecord();
//                        //开始存储到本地种
//                        List<Run> runlist = new ArrayList<Run>();
//                        for(GetDistanceResult.RecordBean recordBean:recordBeanList){
//                            Run run = new Run(null,recordBean.getDistance(),
//                                    recordBean.getAverageSpeed(),recordBean.getPathline(),recordBean.getStartPoint(),
//                                    recordBean.getEndPoint(),recordBean.getTime().split(" ")[0],
//                                    recordBean.getTime().split(" ")[1],true);
//                            runlist.add(run);
//                        }
//                        DBUtil.getRunDao().insertInTx(runlist);
//                        isInsert[1] = true;
//                    }
//                    //进行走步数的存储
//                    retrofit2.Response<GetStepResult> stepRes = ApiUtil.getInstance(Constants.BASE_URL).getStep(UserUtils.getUserId()).execute();
//                    if(stepRes.body().getCode() == Constants.SUCCESS){
//                        //进行存储
//                        List<GetStepResult.RecordBean> recordBeanList = stepRes.body().getRecord();
//                        List<Steps> stepsList = new ArrayList<Steps>();
//                        for(GetStepResult.RecordBean recordBean:recordBeanList){
//                            Steps steps = new Steps(null,recordBean.getStep(),recordBean.getDate(),true);
//                            stepsList.add(steps);
//                        }
//                        DBUtil.getStepsDao().insertInTx(stepsList);
//                        isInsert[2] = true;
//                    }
                    //完成基本工作，进行页面跳转
//                    if(isInsert[0] == true && isInsert[1] == true && isInsert[2] == true){
//                        handler.sendEmptyMessage(SUCCESS);
//                    }else{
//                        handler.sendEmptyMessage(FAILED);
//                    }
                    handler.sendEmptyMessage(SUCCESS);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }


    @Override
    public void failedLogIn() {

        new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                .setTitleText("很抱歉")
                .setContentText("您的用户名或密码输入错误")
                .show();
    }

    @Override
    public void logIn(String uName, String password) {
        presenter.logInToService(uName, password);
    }

    @Override
    public void signIn() {
        startActivity(new Intent(this, SignInActivity.class));
    }


    @OnClick({R.id.login, R.id.register, R.id.qq_login, R.id.wechat_login, R.id.xinlang_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (accountInput.getText().toString().equals("")) {
//                    Snackbar.make(container, "用户名不能为空", Snackbar.LENGTH_SHORT).show();
                    ToastUtil.showToast(this,"用户名不能为空");
                } else if (passwordInput.getText().toString().equals("")) {
//                    Snackbar.make(container, "密码不能为空", Snackbar.LENGTH_SHORT).show();
                    ToastUtil.showToast(this,"密码不能为空");
                } else {
                    presenter.logInToService(accountInput.getText().toString(), passwordInput.getText().toString());
                }
                break;
            case R.id.register:
                signIn();
                break;
            case R.id.qq_login:
                authorize(QQ);
                break;
            case R.id.wechat_login:
                authorize(WECHAT);
                break;
            case R.id.xinlang_login:
                authorize(XINLANG);
                break;
        }
    }
    private final int QQ = 0;
    private final int XINLANG = 1;
    private final int WECHAT = 2;
    public void authorize(int platform){
        Platform pf = null;
        switch (platform){
            case QQ:
                pf = ShareSDK.getPlatform(cn.sharesdk.tencent.qq.QQ.NAME);
                break;
            case XINLANG:
                pf = ShareSDK.getPlatform(SinaWeibo.NAME);
                break;
            case WECHAT:
                pf = ShareSDK.getPlatform(Wechat.NAME);
               break;
        }
        //使用了SSO授权后，有客户端的都会优先启用客户端授权，没客户端的则任然使用网页版进行授权。
        pf.SSOSetting(false);//客户端授权
        pf.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Iterator ite = hashMap.entrySet().iterator();
                while(ite.hasNext()){
                    Map.Entry entry = (Map.Entry) ite.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    Log.i(TAG,key+" "+value);
                }
                if(platform.isAuthValid()){
                    platform.removeAccount(true);
                }
                Intent intent = new Intent(LogInActivity.this,MainViewActivity.class);
                startActivity(intent);
//                String accessToken = db.getToken();
//                String userId = db.getUserId();
//                String platname = db.getPlatformNname();
//                int version = db.getPlatformVersion();
//                String tokenSecret = db.getTokenSecret();
//                String gender = db.getUserGender();
//                String icon = db.getUserIcon();
//                String userName = db.getUserName();
//                String expiresIn = String.valueOf(db.getExpiresIn());
//                String expireseTime = String.valueOf(db.getExpiresTime());
//                Log.i(TAG,accessToken+" "+platname+" "+version+" "+tokenSecret+" "+gender+" "+icon+" "+userName+" "+expiresIn+" "+expireseTime);
//                if(platform.isAuthValid()){
//                    platform.removeAccount(true);
//                }
            }
            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if(platform.isAuthValid()){
                    platform.removeAccount(true);
                }
            }
            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        pf.showUser(null);
    }
}
