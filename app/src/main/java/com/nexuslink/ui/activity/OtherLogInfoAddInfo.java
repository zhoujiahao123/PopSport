package com.nexuslink.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.app.BaseActivity;
import com.nexuslink.model.addinfomodel.AddInfoModelImpl;
import com.nexuslink.model.altermodel.AlterModelImpl;
import com.nexuslink.presenter.addinfopresenter.AddInfoPresenter;
import com.nexuslink.presenter.addinfopresenter.AddInfoPresenterImpl;
import com.nexuslink.presenter.alterpresenter.AlterPresenter;
import com.nexuslink.presenter.alterpresenter.AlterPresenterImpl;
import com.nexuslink.presenter.signinpresenter.SignInPresenter;
import com.nexuslink.ui.view.OtherLogInAddInfoView;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.IdUtil;
import com.nexuslink.util.SharedPrefsUtil;
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
 * Created by ASUS-NB on 2017/5/20.
 */

public class OtherLogInfoAddInfo extends BaseActivity implements OtherLogInAddInfoView{
    @BindView(R.id.user_image)
    CircleImageView userImage;
    @BindView(R.id.height)
    TextView height;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private CharacterPickerWindow window = null;
    //作为标志位，标志是哪一个选择器正在起作用
    private String pickerFlag = null;

    //选择器的数据源
    ArrayList<String> heightList;
    ArrayList<String> weightList;
    private AddInfoPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinfo);
        ButterKnife.bind(this);
        window = new CharacterPickerWindow(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new AddInfoPresenterImpl(new AddInfoModelImpl(),this);
        XLog.e("调试这里");
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

    private void onSelectOption(int position) {
        if (pickerFlag == HEIGHT_TYPE) {
            height.setText(heightList.get(position));
        } else if (pickerFlag == WEIGHT_TYPE) {
            weight.setText(weightList.get(position));
        }
    }

    /**
     * 初始化选择器
     */
    private final static String HEIGHT_TYPE = "HEIGHT";
    private final static String WEIGHT_TYPE = "WEIGHT";

    private void initPickerWindow(String type, int num) {

        if (type == HEIGHT_TYPE) {
            window.getPickerView().setPicker(showHeightAction());
            window.setSelectOptions(num);
        } else if (type == WEIGHT_TYPE) {
            window.getPickerView().setPicker(showWeightAction());
            window.setSelectOptions(num);
        }
        pickerFlag = type;
        window.setOnoptionsSelectListener(new OnOptionChangedListener() {
            @Override
            public void onOptionChanged(int i, int i1, int i2) {
                onSelectOption(i);
            }
        });

    }
    String imagePath = null;
    @OnClick({R.id.user_image, R.id.height, R.id.weight, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_image:
                GalleryFinal.openGallerySingle(0, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        imagePath = resultList.get(0).getPhotoPath();
                        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                        userImage.setImageBitmap(bitmap);
                    }
                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {
                        ToastUtil.showToast(OtherLogInfoAddInfo.this, errorMsg);
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
            case R.id.btn_ok:
                addInfo((int)IdUtil.getuId(), (char) SharedPrefsUtil.getValue(this,"gender","gender",'M'),Integer.valueOf(height.getText().toString()),
                        Integer.valueOf(weight.getText().toString()));
                break;
        }
    }

    @Override
    public void addInfo(int uId, char uGender, int uHeight, int uWeight) {
        presenter.addInfo(uId,uGender,uHeight,uWeight);
    }

    @Override
    public void onAddSucceed() {

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
