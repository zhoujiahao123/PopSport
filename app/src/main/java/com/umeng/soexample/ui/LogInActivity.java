package com.umeng.soexample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.soexample.R;
import com.umeng.soexample.app.BaseActivity;
import com.umeng.soexample.presenter.LogInPresenter;
import com.umeng.soexample.presenter.LogInPresenterImp;
import com.umeng.soexample.ui.view.LoginView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ASUS-NB on 2016/12/17.
 */

public class LogInActivity extends BaseActivity implements LoginView {
    private static final String TAG = "AppCompatActivity";
    @BindView(R.id.layout_login_qq)
    LinearLayout layoutLoginQq;
    @BindView(R.id.layout_login_wechat)
    LinearLayout layoutLoginWechat;
    @BindView(R.id.text_input_name)
    TextInputEditText textInputName;
    @BindView(R.id.text_input_password)
    TextInputEditText textInputPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    private LogInPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LogInPresenterImp();
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(LogInActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
            //在这里打开另一个activity
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

    @OnClick({R.id.layout_login_qq, R.id.layout_login_wechat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_login_qq:
                UMShareAPI mShareAPI = UMShareAPI.get(LogInActivity.this);
                mShareAPI.getPlatformInfo(LogInActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.layout_login_wechat:
                UMShareAPI mShareAPI1 = UMShareAPI.get(LogInActivity.this);
                mShareAPI1.doOauthVerify(LogInActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
        }
    }

    @Override
    public void succeedLogIn() {
        //进入另一个Activity
    }

    @Override
    public void failedLogIn() {
        Toast.makeText(this, "您输入的密码有误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void logIn(String uid,String password) {
        presenter.logInToService(uid,password);
    }

    @Override
    public void signIn() {
        //进入注册activity
    }

    @OnClick({R.id.btn_login, R.id.btn_sign_in})
    public void onClick1(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                logIn(textInputName.getText().toString(),textInputPassword.getText().toString());
                break;
            case R.id.btn_sign_in:
                signIn();
                break;
        }
    }
}
