package com.nexuslink.ui.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toolbar;

import com.nexuslink.R;
import com.nexuslink.model.altermodel.AlterModelImpl;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.presenter.alterpresenter.AlterPresenter;
import com.nexuslink.presenter.alterpresenter.AlterPresenterImpl;
import com.nexuslink.ui.view.AlterView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.IdUtil;
import com.nexuslink.util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ASUS-NB on 2017/2/9.
 */

public class AlterActivity extends SwipeBackActivity implements AlterView {

    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.nick_name)
    TextView nickName;
    private Toolbar toolbar;

    private AlterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isConnective()) {
            Log.e("TAG","true");
            load();
        } else {
            setContentView(R.layout.activity_error_network);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setActionBar(toolbar);
                getActionBar().setDisplayShowTitleEnabled(false);
                toolbar.setNavigationIcon(R.drawable.turn_back);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }

        }

    }

    @Override
    public void showUserInfo(UserInfo userInfo) {
        setContentView(R.layout.activity_alterinfo);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.turn_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
        getActionBar().setDisplayShowTitleEnabled(false);

        ButterKnife.bind(this);
        //设置头像
        ImageUtil.imageDisplay(userInfo.getUser().getUImg(), circleImageView);
        //设置昵称
        nickName.setText(userInfo.getUser().getUName());
    }

    private boolean isConnective() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void showError() {
        setContentView(R.layout.activity_error_server);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.turn_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    private void load() {
        Log.e("TAG","load");
        presenter = new AlterPresenterImpl(new AlterModelImpl(), this);
        presenter.getUserInfo(8);
    }

    @OnClick({R.id.height_relative, R.id.weight_relative, R.id.sex_relative, R.id.password_relative})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.height_relative:
                break;
            case R.id.weight_relative:
                break;
            case R.id.sex_relative:
                break;
            case R.id.password_relative:
                break;
        }
    }

    @OnClick(R.id.log_off)
    public void onClick() {

    }
}
