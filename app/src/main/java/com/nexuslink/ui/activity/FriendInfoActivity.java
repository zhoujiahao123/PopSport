package com.nexuslink.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FriendInfo;
import com.nexuslink.model.friendinfomodel.FriendInfoModelImpl;
import com.nexuslink.presenter.friendinfopresenter.FriendInfoPresenter;
import com.nexuslink.presenter.friendinfopresenter.FriendInfoPresenterImpl;
import com.nexuslink.ui.view.FriendInfoView;
import com.nexuslink.util.BlurDrawable;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.ImageUtil;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ASUS-NB on 2017/2/7.
 */

public class FriendInfoActivity extends SwipeBackActivity implements FriendInfoView {

    private final static int BACKGROUD=1;
    @BindView(R.id.first)
    LinearLayout first;
    @BindView(R.id.friend_head_background)
    RelativeLayout friendHeadBackground;
    //辅助变量,用于被模糊的背景
    ImageView imageViewBackground;
    @BindView(R.id.friend_head_image)
    CircleImageView friendHeadImage;
    @BindView(R.id.turn_back)
    ImageView turnBack;
    //用户获取头像文件的输入流
    private OkHttpClient okHttpClient = new OkHttpClient();
    private FriendInfoPresenter presenter;
    private String headPath;
    private String nickName;

    private InputStream inputStream;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==BACKGROUD){
                doBlur(inputStream);
            }

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_friendinfo);
        ButterKnife.bind(this);
        headPath = getIntent().getStringExtra("uImg");
        nickName  = getIntent().getStringExtra("uName");
        initView();

    }

    private void initView() {
          imageViewBackground = new ImageView(this);
        getHeadImage(Constants.PHOTO_BASE_URL+headPath,friendHeadImage);
        getImageStream();
        XLog.e(Constants.PHOTO_BASE_URL+headPath);

    }

    /**
     * 获取头像
     */
    private void getHeadImage(String url, ImageView imageView) {
        ImageUtil.imageDisplay(url, imageView);
        XLog.e("成功c");
    }

    /**
     * 以头像作为背景 并做模糊化处理
     */
    private void doBlur(InputStream inputStream) {
        XLog.e("Doblur");
        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        final BlurDrawable blurDrawable = new BlurDrawable(this, getResources(), bitmap);
        blurDrawable.setBlur(200);XLog.e("Doblur");
        imageViewBackground.setImageDrawable(blurDrawable.getBlurDrawable());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            friendHeadBackground.setBackground(imageViewBackground.getDrawable());
        }
    }

    @Override
    public void showFriendInfo(FriendInfo friendInfo) {

    }

    private void getImageStream() {
        Request request = new Request.Builder().url(Constants.PHOTO_BASE_URL+headPath).get().build();
        Call call=okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                XLog.e("成功");
                if(response.isSuccessful()){
                    inputStream = response.body().byteStream();
                    Message message = new Message();
                    message.what=BACKGROUD;
                    handler.sendMessage(message);
                }
            }
        });
    }

    @OnClick(R.id.turn_back)
    public void onClick(View v) {
        if(v.getId()==R.id.turn_back){
            onBackPressed();
        }
    }
}
