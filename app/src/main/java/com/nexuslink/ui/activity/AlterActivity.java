package com.nexuslink.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import com.nexuslink.R;
import com.nexuslink.User;
import com.nexuslink.UserDao;
import com.nexuslink.app.BaseApplication;
import com.nexuslink.config.Constants;
import com.nexuslink.model.altermodel.AlterModelImpl;
import com.nexuslink.model.data.ChangeInfo;
import com.nexuslink.model.data.ChangeInfo1;
import com.nexuslink.model.data.EventEntry;
import com.nexuslink.model.data.ImgInfo;
import com.nexuslink.model.data.Info;
import com.nexuslink.model.data.UserInfo;
import com.nexuslink.presenter.alterpresenter.AlterPresenter;
import com.nexuslink.presenter.alterpresenter.AlterPresenterImpl;
import com.nexuslink.service.StepService;
import com.nexuslink.ui.dialog.AlterPasswordDialog;
import com.nexuslink.ui.view.AlterView;
import com.nexuslink.util.ActivityStack;
import com.nexuslink.util.BitmapCompressUpUtils;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.DBUtil;
import com.nexuslink.util.GlideCacheUtil;
import com.nexuslink.util.IdUtil;
import com.nexuslink.util.ImageUtil;
import com.nexuslink.util.SharedPrefsUtil;
import com.nexuslink.util.ToastUtil;
import com.nexuslink.util.loader.LoaderFactory;
import com.wevey.selector.dialog.MDEditDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jeesoft.widget.pickerview.CharacterPickerWindow;
import cn.jeesoft.widget.pickerview.OnOptionChangedListener;
import me.drakeet.materialdialog.MaterialDialog;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ASUS-NB on 2017/2/9.
 */

public class AlterActivity extends SwipeBackActivity implements AlterView, AlterPasswordDialog.OnRightClickListener {

    private final static String HEIGHT_TYPE = "HEIGHT";
    private final static String WEIGHT_TYPE = "WEIGHT";
    private final static String GENDER_TYPE = "GENDER";
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.height)
    TextView height;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.container)
    LinearLayout container;

    private Toolbar toolbar;

    private AlterPresenter presenter;

    MDEditDialog dialog;

    private String nickNamePreper;

    private OkHttpClient okHttpClient = new OkHttpClient();

    private CharacterPickerWindow window = null;

    //选择器的数据源
    ArrayList<String> heightList;
    ArrayList<String> weightList;
    ArrayList<String> genderList;

    //作为标志位，标志是哪一个选择器正在起作用
    private String pickerFlag = null;
    int uWeight;
    int uHeight;
    private static final int COMPRESS_IMAGE_SUCCESS = 2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                nickName.setText(BaseApplication.getDaosession().getUserDao().queryBuilder().where(
                        UserDao.Properties.Already.eq(1)
                ).unique().getUName());
            } else if (msg.what == COMPRESS_IMAGE_SUCCESS) {
                postPhoto((String) msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isConnective()) {
            presenter = new AlterPresenterImpl(new AlterModelImpl(), this);
            load();
        } else {
            presenter = new AlterPresenterImpl(new AlterModelImpl(), this);
            setContentView(R.layout.activity_error_network);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar = (Toolbar) findViewById(R.id.toolbar);
                setActionBar(toolbar);
                getActionBar().setDisplayShowTitleEnabled(false);
                getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //add top-left icon click event deal
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        XLog.e("onStart");
//        ImageUtil.imageDisplayHeadImage(Constants.PHOTO_BASE_URL + BaseApplication.getDaosession().getUserDao().queryBuilder().where(
//                UserDao.Properties.Already.eq(1)
//        ).unique().getUImg(), circleImageView);

    }

    @Override
    public void showUserInfo(UserInfo userInfo) {
        setContentView(R.layout.activity_alterinfo);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(toolbar);
        }
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        window = new CharacterPickerWindow(this);

        //设置头像
        ImageUtil.imageDisplayHeadImage(Constants.PHOTO_BASE_URL + userInfo.getUser().getUImg(), circleImageView);
        //设置昵称
        XLog.e(userInfo.getUser().getUHeight());
        XLog.e(userInfo.getUser().getUWeight());
        float f = userInfo.getUser().getUHeight();
        uHeight = (int) f;
        float w = userInfo.getUser().getUWeight();
        uWeight = (int) w;
        height.setText(uHeight + "cm");
        weight.setText(uWeight + "kg");
        nickName.setText(userInfo.getUser().getUName());
        gender.setText(String.valueOf(userInfo.getUser().getUGender().toString().equals("M") ? '男' : '女'));
    }

    public void showUserInfo(User user) {
        setContentView(R.layout.activity_alterinfo);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setActionBar(toolbar);
        }
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        window = new CharacterPickerWindow(this);

        //设置头像
        ImageUtil.imageDisplayHeadImage(Constants.PHOTO_BASE_URL + user.getUImg(), circleImageView);
//        LoaderFactory.getGlideLoader().loadNet(circleImageView,Constants.PHOTO_BASE_URL+user.getUImg(),new ILoader.Option(R.drawable.head_photo,R.drawable.head_photo

        //设置昵称
        float f = user.getUHeight();
        uHeight = (int) f;
        float w = user.getUWeight();
        uWeight = (int) w;
        height.setText(uHeight + "cm");
        weight.setText(uWeight + "kg");
        nickName.setText(user.getUName());
        gender.setText(String.valueOf(user.getUGender().toString().equals("M") ? '男' : '女'));
    }

    private void initPickerWindow(String type, int num) {

        if (type == HEIGHT_TYPE) {
            window.getPickerView().setPicker(showHeightAction());
            window.setSelectOptions(num);
        } else if (type == WEIGHT_TYPE) {
            window.getPickerView().setPicker(showWeightAction());
            window.setSelectOptions(num);
        } else if (type == GENDER_TYPE) {
            window.getPickerView().setPicker(showGenderAction());

        }
        pickerFlag = type;
        window.setOnoptionsSelectListener(new OnOptionChangedListener() {
            @Override
            public void onOptionChanged(int i, int i1, int i2) {
                onSelectOption(i);
            }
        });

    }

    private void onSelectOption(int position) {
        XLog.e(height.getText().toString());
        XLog.e(weight.getText().toString());
        if (pickerFlag == HEIGHT_TYPE) {
            height.setText(heightList.get(position));
        } else if (pickerFlag == WEIGHT_TYPE) {
            weight.setText(weightList.get(position));
        } else if (pickerFlag == GENDER_TYPE) {
            gender.setText(genderList.get(position));
        }
        presenter.changeUserInfo((int) IdUtil.getuId(), gender.getText().toString().equals("男") ? 'M' : 'W', Integer.valueOf(height.getText().toString().replaceAll("cm", " ").trim()),
                Integer.valueOf(weight.getText().toString().replaceAll("kg", " ").trim()));
    }

    /**
     * 选择器的数据源
     *
     * @return
     */
    private ArrayList<String> showHeightAction() {
        heightList = new ArrayList<>();
        for (int i = 0; i < 191; i++) {
            heightList.add(i + 60 + "cm");
        }
        return heightList;
    }

    private ArrayList<String> showWeightAction() {
        weightList = new ArrayList<>();
        for (int i = 0; i < 70; i++) {
            weightList.add(i + 30 + "kg");
        }
        return weightList;
    }

    private ArrayList<String> showGenderAction() {
        genderList = new ArrayList<>();
        genderList.add("男");
        genderList.add("女");
        return genderList;
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
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void changeUserInfo(int uId, char uGender, int uHeight, int uWeight) {
        presenter.changeUserInfo(uId, uGender, uHeight, uWeight);
    }

    @Override
    public void showChangeUserInfo(ChangeInfo changeInfo) {

    }

    @Override
    public void changeNickName(int uId, String uName) {
        presenter.changeNickName(uId, uName);
        nickNamePreper = uName;
    }

    @Override
    public void showChangeNickName(ChangeInfo1 changeInfo1) {
        if (changeInfo1.getCode() != 500) {
            if (changeInfo1.getChangeFlag() == 1) {
                nickName.setText(nickNamePreper);
            } else {
                ToastUtil.showToast(this, "用户名重复");
            }
        }
    }

    private void load() {
        EventBus.getDefault().register(this);
        UserDao userDao = BaseApplication.getDaosession().getUserDao();
        User user = userDao.queryBuilder().where(UserDao.Properties.Already.eq(1)).unique();
        if (user.getUName() == null) {
            presenter.getUserInfo((int) IdUtil.getuId());
        } else {
            showUserInfo(user);
        }

    }

    @OnClick({R.id.password_relative, R.id.head_relative, R.id.name_relative, R.id.weight, R.id.height, R.id.gender})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.height:
                initPickerWindow(HEIGHT_TYPE, uHeight - 60);
                window.showAtLocation(container, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.weight:
                initPickerWindow(WEIGHT_TYPE, uWeight - 30);
                window.showAtLocation(container, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.gender:
                initPickerWindow(GENDER_TYPE, 0);
                window.showAtLocation(container, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.password_relative:
                final MaterialDialog dialog1 = new MaterialDialog(this);
                dialog1.setContentView(R.layout.dialog_password);
                View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_password, null);
//                builder.setView(view1);
                final TextInputEditText oldPassword = (TextInputEditText) view1.findViewById(R.id.old_password);
                final TextInputEditText newPassword = (TextInputEditText) view1.findViewById(R.id.new_password);
                final TextInputEditText newPasswordAgain = (TextInputEditText) view1.findViewById(R.id.new_password_again);
                dialog1.setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (oldPassword.getText().toString().equals("") || newPassword.getText().toString().equals("") || newPasswordAgain.getText().toString().equals("")) {
                            ToastUtil.showToast(AlterActivity.this, "请将密码补充完整");
                        } else if (!newPassword.getText().toString().equals(newPasswordAgain.getText().toString())) {
                            ToastUtil.showToast(AlterActivity.this, "两次输入的密码不一致");
                        } else {
                            presenter.changePassword(8, oldPassword.getText().toString(), newPassword.getText().toString());
                            dialog1.dismiss();
                        }
                    }
                });
                dialog1.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
                break;
            case R.id.head_relative:
                startActivity(new Intent(this, AlterPhotoActivity.class));
                break;
            case R.id.name_relative:
                dialog = new MDEditDialog.Builder(AlterActivity.this)
                        .setTitleVisible(true)
                        .setTitleText("修改用户名")
                        .setTitleTextSize(20)
                        .setTitleTextColor(R.color.colorAccent)
                        .setContentTextSize(18)
                        .setMaxLength(7)
                        .setMaxLines(1)
                        .setContentTextColor(R.color.primary_text)
                        .setButtonTextSize(14)
                        .setLeftButtonTextColor(R.color.secondary_text)
                        .setLeftButtonText("取消")
                        .setRightButtonTextColor(R.color.primary_text)
                        .setRightButtonText("确定")
                        .setInputTpye(InputType.TYPE_CLASS_TEXT)
                        .setLineColor(R.color.colorAccent)
                        .setOnclickListener(new MDEditDialog.OnClickEditDialogListener() {
                            @Override
                            public void clickLeftButton(View view, String text) {
                                dialogDismiss();
                            }

                            @Override
                            public void clickRightButton(View view, String text) {
                                changeNickName((int) IdUtil.getuId(), text);
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
//        SharedPreferences sharedPreferences = getSharedPreferences("already",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove("already");
//        editor.putInt("already",0);
//        editor.commit();
        android.app.AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("您确定要退出吗?");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //停止运行service
                Intent intent1 = new Intent(AlterActivity.this, StepService.class);
                stopService(intent1);
                SharedPrefsUtil.putValue(AlterActivity.this, "already", "already", 0);
                DBUtil.getUserDao().deleteAll();
                DBUtil.getRunDao().deleteAll();
                DBUtil.getStepsDao().deleteAll();

                Intent intent = new Intent(AlterActivity.this, LogInActivity.class);
                startActivity(intent);
                onBackPressed();
                //退出之前清除所有Activity
                ActivityStack.getScreenManager().clearAllActivity();

            }
        });
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClicked(String oldPssword, String newPassword) {

    }

    @Override
    public void onDismiss() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    String realPath;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void photoChooseEvent(EventEntry eventEntry) {
        final String photoPath = eventEntry.photos.get(0).getPath();
        realPath = photoPath;
//        ImageUtil.imageDisplayWithFile(new File(photoPath), circleImageView);
        eventEntry.photos.clear();
        XLog.e("上面这个执行了");
//        LoaderFactory.getGlideLoader().loadFile(circleImageView,new File(photoPath),null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = BitmapCompressUpUtils.compressImage(photoPath, photoPath + "111", 50);
                Message msg = Message.obtain(null, COMPRESS_IMAGE_SUCCESS, str);
                handler.sendMessage(msg);
            }
        }).start();
    }


    private void postPhoto(final String path) {
        LoaderFactory.getGlideLoader().clearCacheMemory(AlterActivity.this);
        String type = "image/jpeg";
        File file = new File(path);
        String str = file.getName().split("\\.")[1];
        if (str.equals("png")) {
            type = "image/png";
        }
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("uId", String.valueOf(IdUtil.getuId()))
                .addFormDataPart("uImg", "uImg", RequestBody.create(MediaType.parse(type), file)).build();
        Request request = new Request.Builder().post(body).url("http://120.77.87.78:8080/arti-sports/api/img/changeImg").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deletePhoto(path);
                XLog.e("上传头像失败了");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                User user = BaseApplication.getDaosession().getUserDao().queryBuilder().where(UserDao.Properties.Already.eq(1)).unique();
                Gson gson = new Gson();
                final ImgInfo imgInfo = gson.fromJson(response.body().string(), ImgInfo.class);
                XLog.e("上传头像成功了");
                if (imgInfo.getCode() == 500) {
                    Snackbar.make(container, "未知的错误发生了", Snackbar.LENGTH_SHORT).show();
                    deletePhoto(path);
                } else {
                    user.setUImg(imgInfo.getUserImg());
                    XLog.e("");

                    Message message = new Message();
                    message.what = 1;
                    message.obj = imgInfo.getUserImg();
                    handler.sendMessage(message);
                    BaseApplication.getDaosession().getUserDao().update(user);
                    XLog.e("这里已经把头像插进去了");

                    //由于更换了头像，需要进行页面刷新，所以在这里讲所有缓存清除
                    GlideCacheUtil.getInstance().clearImageDiskCache(AlterActivity.this);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GlideCacheUtil.getInstance().clearImageMemoryCache(AlterActivity.this);
                            Glide.with(AlterActivity.this).load(realPath).into(circleImageView);//重新设置头
                        }
                    });
                    deletePhoto(path);
                    SharedPrefsUtil.putValue(AlterActivity.this, "userinfo", "userImage", imgInfo.getUserImg());
                    EventBus.getDefault().post(new Info());
                }
            }
        });
    }


    private void deletePhoto(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 关闭dialog
     */
    private void dialogDismiss() {
        dialog.dismiss();
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String message) {

    }
}
