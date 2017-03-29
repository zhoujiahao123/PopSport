package com.nexuslink.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.nexuslink.util.IdUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.IOException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
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
    @Override
    public void succeedLogIn() {
        RequestBody body =new FormBody.Builder().add("uId", String.valueOf(IdUtil.getuId())).build();
        Request request = new Request.Builder().post(body).url(Constants.BASE_URL+"user/getInfo").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                XLog.e(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();
                XLog.e("登录时存储用户数据成功"+content);
                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(content,UserInfo.class);
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
        }
        });
        editor.putInt("already",1);
        editor.commit();
        Intent mainViewIntent = new Intent(LogInActivity.this, MainViewActivity.class);
        startActivity(mainViewIntent);
        finish();
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
