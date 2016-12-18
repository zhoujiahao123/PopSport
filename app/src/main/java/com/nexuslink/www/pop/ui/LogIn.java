package com.nexuslink.www.pop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nexuslink.www.pop.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ASUS-NB on 2016/12/17.
 */

public class LogIn extends AppCompatActivity {
    private static final String TAG = "AppCompatActivity";
    @BindView(R.id.layout_login_qq)
    LinearLayout layoutLoginQq;
    @BindView(R.id.layout_login_wechat)
    LinearLayout layoutLoginWechat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
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
                UMShareAPI  mShareAPI = UMShareAPI.get( LogIn.this );
                mShareAPI.getPlatformInfo(LogIn.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.layout_login_wechat:
                UMShareAPI  mShareAPI1 = UMShareAPI.get( LogIn.this );
                mShareAPI1.getPlatformInfo(LogIn.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
        }
    }
}
