package com.nexuslink.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.app.BaseActivity;
import com.nexuslink.presenter.loginpresenter.LogInPresenterImp;
import com.nexuslink.presenter.loginpresenter.LoginPresenter;
import com.nexuslink.ui.view.LoginView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.SharedPrefsUtil;
import com.nexuslink.util.ToastUtil;

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


public class LogInActivity extends BaseActivity implements LoginView {
    private static final String TAG = "AppCompatActivity";
    @BindView(R.id.user_image)
    CircleImageView mUserImage;
    @BindView(R.id.login)
    Button mLoginBt;
    @BindView(R.id.register)
    TextView mRegisterTv;

    @BindView(R.id.qq_login)
    CircleImageView qqLogin;
    @BindView(R.id.xinlang_login)
    CircleImageView xinlangLogin;
    @BindView(R.id.account_input)
    EditText accountInput;
    @BindView(R.id.password_input)
    EditText passwordInput;

    private SweetAlertDialog sweetAlertDialog;
    private LoginPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        if (SharedPrefsUtil.getValue(this, "already", "already", 0) == 1) {
            startActivity(new Intent(this, MainViewActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
            sweetAlertDialog = new SweetAlertDialog(this);
            presenter = new LogInPresenterImp(this);
        }
    }

    AlertDialog.Builder builder;
    AlertDialog dialog;

    /**
     * 第三方登录
     *
     * @param view
     */
    @OnClick({R.id.login, R.id.register, R.id.qq_login ,R.id.xinlang_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (accountInput.getText().toString().equals("")) {
                    sweetAlertDialog.setContentText("用户名不能为空哦...");
                    sweetAlertDialog.show();
                } else if (passwordInput.getText().toString().equals("")) {
                    sweetAlertDialog.setContentText("密码不能为空哦...");
                } else {
                    presenter.login(accountInput.getText().toString(), passwordInput.getText().toString());
                }
                break;
            case R.id.register:
                Intent intent = new Intent(this,SignInActivity.class);
                startActivity(intent);
                SharedPrefsUtil.putValue(this,"firstlogin","firstlogin",true);
                break;
            case R.id.qq_login:
                authorize(QQ);
                break;
            case R.id.xinlang_login:
                authorize(XINLANG);
                break;
        }
    }

    private final int QQ = 0;
    private final int XINLANG = 1;
    private final int WECHAT = 2;

    public void authorize(int platform) {
        Platform pf = null;
        switch (platform) {
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
                while (ite.hasNext()) {
                    Map.Entry entry = (Map.Entry) ite.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    Log.i(TAG, key + " " + value);
                    ToastUtil.showToast(LogInActivity.this,"key:"+key+
                                "value:"+value);
                }
                if (platform.isAuthValid()) {
                    platform.removeAccount(true);
                }
//                Intent intent = new Intent(LogInActivity.this,MainViewActivity.class);
//                startActivity(intent);
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
                if (platform.isAuthValid()) {
                    platform.removeAccount(true);
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        pf.showUser(null);
    }


    @Override
    public void loginSuccess() {
        SharedPrefsUtil.putValue(LogInActivity.this, "already", "already", 1);
        Intent mainViewIntent = new Intent(LogInActivity.this, MainViewActivity.class);
        startActivity(mainViewIntent);
        //登陆成功发送广播,通知计步更新
        sendBroadcast(new Intent("ANewacount"));
        finish();
    }

    @Override
    public void loginFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sweetAlertDialog.setTitle("很抱歉");
                sweetAlertDialog.setContentText("登陆过程中出现错误");
                sweetAlertDialog.show();
            }
        });
    }

    @Override
    public void showProgress() {
        //进行提示
        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        final TextView tv = new TextView(this);
        tv.setText("请稍候，我们正在为您做一些初始工作...");
        builder.setView(tv);
        dialog.show();
    }

    @Override
    public void hideProgress() {
        if(dialog == null){
            return;
        }
        dialog.dismiss();
        dialog = null;
    }

    @Override
    public void showMsg(String message) {

    }

}
