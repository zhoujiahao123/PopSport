package com.nexuslink.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.nexuslink.R;
import com.nexuslink.app.BaseActivity;
import com.nexuslink.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ASUS-NB on 2017/3/5.
 */

public class NoNetActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_reload)
    Button btnReload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_network_normal);
        ButterKnife.bind(this);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkActive()){
                    finish();
                }else {
                    ToastUtil.showToast(NoNetActivity.this,"哎呀，网络连接有问题");
                }
            }
        });
    }

}
