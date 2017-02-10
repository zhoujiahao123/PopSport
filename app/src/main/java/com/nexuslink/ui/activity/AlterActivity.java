package com.nexuslink.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.litao.android.lib.entity.PhotoEntry;
import com.nexuslink.R;
import com.nexuslink.model.altermodel.AlterModelImpl;
import com.nexuslink.model.data.ChangeInfo;
import com.nexuslink.model.data.EventEntry;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.presenter.alterpresenter.AlterPresenter;
import com.nexuslink.presenter.alterpresenter.AlterPresenterImpl;
import com.nexuslink.ui.view.AlterView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.ImageUtil;
import com.nexuslink.util.ToastUtil;
import com.wevey.selector.dialog.MDEditDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

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

    MDEditDialog dialog;

    private String nickNamePreper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isConnective()) {
            Log.e("TAG", "true");
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

    @Override
    public void changeUserInfo(int uId, String uName, char uGender, float uHeight, float uWeight) {
        presenter.changeUserInfo(uId,uName,uGender,uHeight,uWeight);
    }

    @Override
    public void showChangeUserInfo(ChangeInfo changeInfo) {
        if(changeInfo.getCode()==200){
            if(changeInfo.getChangeFlag()==0){
                nickName.setText(nickNamePreper);
            }else {
                ToastUtil.showToast(this,"该用户名已被占用");
            }
        }else {
            ToastUtil.showToast(this,"未知的错误出现了");
        }
    }

    private void load() {
        EventBus.getDefault().register(this);
        presenter = new AlterPresenterImpl(new AlterModelImpl(), this);
        presenter.getUserInfo(8);
    }

    @OnClick({R.id.height_relative, R.id.weight_relative, R.id.sex_relative, R.id.password_relative, R.id.head_relative, R.id.name_relative})
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
            case R.id.head_relative:
                startActivity(new Intent(this, AlterPhotoActivity.class));
                break;
            case R.id.name_relative:
                dialog = new MDEditDialog.Builder(AlterActivity.this)
                        .setTitleVisible(true)
                        .setTitleText("修改用户名")
                        .setTitleTextSize(20)
                        .setTitleTextColor(R.color.black_light)
                        .setContentTextSize(18)
                        .setMaxLength(7)
                        .setMaxLines(1)
                        .setContentTextColor(R.color.colorPrimary)
                        .setButtonTextSize(14)
                        .setLeftButtonTextColor(R.color.colorPrimary)
                        .setLeftButtonText("取消")
                        .setRightButtonTextColor(R.color.colorPrimary)
                        .setRightButtonText("确定")
                        .setInputTpye(InputType.TYPE_CLASS_TEXT)
                        .setLineColor(R.color.colorPrimary)
                        .setOnclickListener(new MDEditDialog.OnClickEditDialogListener() {
                            @Override
                            public void clickLeftButton(View view, String text) {
                                dialogDismiss();
                            }
                            @Override
                            public void clickRightButton(View view, String text) {
                                changeUserInfo(8,"Jacob",'M',180,57);
                                nickNamePreper = text;
                                dialogDismiss();
                            }
                        })
                        .setMinHeight(0.3f)
                        .setWidth(0.8f)
                        .build();
                dialog.show();
                break;
        }
    }

    @OnClick(R.id.log_off)
    public void onClick() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void photoChooseEvent(EventEntry eventEntry) {
        String photoPath = eventEntry.photos.get(0).getPath();
        ImageUtil.imageDisplayWithFile(new File(photoPath), circleImageView);
        eventEntry.photos.clear();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void photoChoosesEvent(PhotoEntry entry) {
        String photoPath = entry.getPath();
        ImageUtil.imageDisplayWithFile(new File(photoPath), circleImageView);
    }

    /**
     * 关闭dialog
     */
    private void dialogDismiss() {
        dialog.dismiss();
    }
}
