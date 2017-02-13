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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.elvishew.xlog.XLog;
import com.litao.android.lib.entity.PhotoEntry;
import com.nexuslink.R;
import com.nexuslink.model.altermodel.AlterModelImpl;
import com.nexuslink.model.data.ChangeInfo;
import com.nexuslink.model.data.ChangeInfo1;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    private OkHttpClient okHttpClient = new OkHttpClient();

    private MaterialSpinner heightSpinner;
    private MaterialSpinner weightSpinner;
    private MaterialSpinner sexSpinner;

    //暂时保存选择的spinner的位置
    private int heightSpinnerPosition;
    private int weightSpinnerPosition;
    private int sexSpinnerPosition;


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
        ImageUtil.imageDisplayHeadImage(userInfo.getUser().getUImg(), circleImageView);
        //设置昵称
        nickName.setText(userInfo.getUser().getUName());
        initDataHeight();
        initDataWeight();
        initDataSex();
        XLog.e("身高为"+userInfo.getUser().getUHeight());
        XLog.e("体重为"+userInfo.getUser().getUWeight());
        XLog.e("性别为"+userInfo.getUser().getUGender());
        if(userInfo.getUser().getUHeight()!=null)
        heightSpinner.setSelection((int)(userInfo.getUser().getUHeight()-60));
        if(userInfo.getUser().getUWeight()!=null)
        weightSpinner.setSelection((int)(userInfo.getUser().getUWeight()-30));
        if(userInfo.getUser().getUGender().equals("M")){
            sexSpinner.setSelection(0);
            XLog.e("是男生");
        }else if(userInfo.getUser().getUGender().equals("W")){
            sexSpinner.setSelection(1);
            XLog.e("是女生");
        }
    }

    /**
     * 身高spinner的配置
     */
    private void initDataHeight(){
        heightSpinner = (MaterialSpinner) findViewById(R.id.spinner1);
        String[] items = new String[191];
        for(int i = 0; i < items.length; i++)
            items[i] = i+60+"";
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        heightSpinner.setAdapter(adapter);
        heightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                heightSpinnerPosition=i;
                if(i!=-1){
                    presenter.changeUserInfo(8,'M',(float)(i+60),57.0f);
                    XLog.e("身高选择");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * 体重spinner的配置
     * @return
     */
    private void initDataWeight(){
        weightSpinner = (MaterialSpinner) findViewById(R.id.spinner2);
        String[] items = new String[71];
        for(int i = 0; i < items.length; i++)
            items[i] = i+30+"";
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        weightSpinner.setAdapter(adapter);
        weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                weightSpinnerPosition = i;
                if(i!=-1){
                    presenter.changeUserInfo(8,'M',180.0f,(float)(i+30));
                    XLog.e("体重选择");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * 性别spinner的配置
     * @return
     */
    private void initDataSex(){
        sexSpinner = (MaterialSpinner) findViewById(R.id.spinner3);
        String[] items = new String[2];
        items[0]="男";
        items[1] = "女";
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);
        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                XLog.e("i 是"+l);
                if(l!=-1){
                    presenter.changeUserInfo(8,i==0?'M':'W',180.0f,57.0f);
                    XLog.e("性别选择");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
    public void changeUserInfo(int uId,  char uGender, float uHeight, float uWeight) {
        presenter.changeUserInfo(uId,uGender,uHeight,uWeight);
    }

    @Override
    public void showChangeUserInfo(ChangeInfo changeInfo) {

    }

    @Override
    public void changeNickName(int uId, String uName) {
        presenter.changeNickName(uId,uName);
        nickNamePreper = uName;
    }

    @Override
    public void showChangeNickName(ChangeInfo1 changeInfo1) {
        if(changeInfo1.getCode()!=500){
            XLog.i("修改昵称返回的code为500");
            if(changeInfo1.getChangeFlag()==1){
                XLog.i("修改用户名成功");
                nickName.setText(nickNamePreper);
            }else {
                ToastUtil.showToast(this,"用户名重复");
            }
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
                                changeNickName(8,text);
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
        postPhoto(photoPath);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void photoChoosesEvent(PhotoEntry entry) {
        String photoPath = entry.getPath();
        ImageUtil.imageDisplayWithFile(new File(photoPath), circleImageView);
        postPhoto(photoPath);
    }

    private void postPhoto(String path){
        File file  = new File(path);
        MultipartBody body =new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("uId","8")
                .addFormDataPart("uImg","uImg",RequestBody.create(MediaType.parse("application/octet-stream"),file)).build();
        Request request = new Request.Builder().post(body).url("http://120.77.87.78:8080/arti-sports/api/img/changeImg").build();
        Call call  =  okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                XLog.i("上传头像失败"+e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                XLog.i("上传头像成功");
                XLog.e(response.body().string());
            }
        });
        }
    /**
     * 关闭dialog
     */
    private void dialogDismiss() {
        dialog.dismiss();
    }
}
