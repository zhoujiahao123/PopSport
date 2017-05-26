package com.nexuslink.ui.activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nexuslink.R;
import com.nexuslink.app.BaseActivity;
import com.nexuslink.model.signinmodel.SignInModleImpl;
import com.nexuslink.presenter.signinpresenter.SignInPresenter;
import com.nexuslink.presenter.signinpresenter.SignInPresenterImpl;
import com.nexuslink.ui.view.SignInView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.jeesoft.widget.pickerview.CharacterPickerWindow;
import cn.jeesoft.widget.pickerview.OnOptionChangedListener;

/**
 * Created by ASUS-NB on 2016/12/20.
 */

public class SignInActivity extends BaseActivity implements SignInView {
    private final static String HEIGHT_TYPE = "HEIGHT";
    private final static String WEIGHT_TYPE = "WEIGHT";
    private final static String GENDER_TYPE = "GENDER";
    private final String TAG = "SignInActivity";
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.height)
    TextView height;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nick_name)
    TextInputEditText nickName;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.user_image)
    CircleImageView mImage;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.private_info)
    TextView privateInfo;
    @BindView(R.id.height_layout)
    RelativeLayout heightLayout;
    @BindView(R.id.weight_layout)
    RelativeLayout weightLayout;
    @BindView(R.id.gender_layout)
    RelativeLayout genderLayout;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;

    private CharacterPickerWindow window = null;
    //作为标志位，标志是哪一个选择器正在起作用
    private String pickerFlag = null;

    //选择器的数据源
    ArrayList<String> heightList;
    ArrayList<String> weightList;
    ArrayList<String> genderList;

    private SignInPresenter presenter;


    private ProgressBar progressBar;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        window = new CharacterPickerWindow(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new SignInPresenterImpl(new SignInModleImpl(), this);
    }

    /**
     * 初始化选择器
     */
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSelectOption(int position) {
        if (pickerFlag == HEIGHT_TYPE) {
            height.setText(heightList.get(position));
        } else if (pickerFlag == WEIGHT_TYPE) {
            weight.setText(weightList.get(position));
        } else if (pickerFlag == GENDER_TYPE) {
            gender.setText(genderList.get(position));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

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

    @Override
    public void requestSignIn(String uName, String uPassword, char uGender, int uHeight, int uWeight, String imagePath) {
        presenter.requestRegister(uName, uPassword, uGender, uHeight, uWeight, imagePath);
    }

    @Override
    public void failedSignIn(String message) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (isNetworkActive()) {
            Snackbar.make(container, message, Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(container, "请检查您的网络", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void succeedSignIn() {
        mProgressBar.setVisibility(View.INVISIBLE);
        onBackPressed();
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    String imagePath = null;

    @OnClick({R.id.height, R.id.weight, R.id.gender, R.id.register, R.id.user_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_image:
                GalleryFinal.openGallerySingle(0, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        imagePath = resultList.get(0).getPhotoPath();
                        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                        mImage.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {
                        ToastUtil.showToast(SignInActivity.this, errorMsg);
                    }
                });
                break;
            case R.id.height:
                initPickerWindow(HEIGHT_TYPE, 110);
                window.showAtLocation(container, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.weight:
                initPickerWindow(WEIGHT_TYPE, 20);
                window.showAtLocation(container, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.gender:
                initPickerWindow(GENDER_TYPE, 0);
                window.showAtLocation(container, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.register:
                if (nickName.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Snackbar.make(container, "用户名或者密码不能为空", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (imagePath == null) {
                        Snackbar.make(container, "要选择头像才能注册哦...", Snackbar.LENGTH_SHORT).show();
                    } else {
                        requestSignIn(nickName.getText().toString(), password.getText().toString(), gender.getText().equals("男") ? 'M' : 'W', Integer.valueOf(height.getText().toString().replaceAll("cm", " ").trim()),
                                Integer.valueOf(weight.getText().toString().replaceAll("kg", " ").trim())
                                , imagePath);
                        showProgressBar();
                    }
                }
                break;
        }
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
