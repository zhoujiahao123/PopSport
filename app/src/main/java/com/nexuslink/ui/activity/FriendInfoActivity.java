package com.nexuslink.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elvishew.xlog.XLog;
import com.nexuslink.R;
import com.nexuslink.config.Constants;
import com.nexuslink.model.data.FollowedInfo;
import com.nexuslink.model.data.FriendInfo;
import com.nexuslink.model.data.SearchInfo;
import com.nexuslink.model.friendmodel.FriendModelImpl;
import com.nexuslink.model.myfriendmodel.OnNumberChangedLisntener;
import com.nexuslink.presenter.friendpresenter.FriendPresenter;
import com.nexuslink.presenter.friendpresenter.FriendPresenterImpl;
import com.nexuslink.ui.adapter.MyFriendInfoPagerAdapter;
import com.nexuslink.ui.view.FriendInfoView;
import com.nexuslink.ui.view.FriendView;
import com.nexuslink.util.BlurDrawable;
import com.nexuslink.util.CircleImageView;
import com.nexuslink.util.ImageUtil;
import com.nexuslink.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
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

public class FriendInfoActivity extends SwipeBackActivity implements FriendInfoView, FriendView {

    private final static int BACKGROUD = 1;
    @BindView(R.id.friend_head_image)
    CircleImageView friendHeadImage;
    @BindView(R.id.turn_back)
    ImageView turnBack;
    @BindView(R.id.friend_head_nick_name)
    TextView friendHeadNickName;
    @BindView(R.id.friend_head_background)
    RelativeLayout friendHeadBackground;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.container)
    RelativeLayout container;

    //用户获取头像文件的输入流
    private OkHttpClient okHttpClient = new OkHttpClient();
    private String headPath;
    private String nickName;
    private int uId;

    private InputStream inputStream;
    private ImageView imageViewBackground;
    private FriendPresenter friendPresenter;

    //用于装载输入流
    private byte[] data;

    MyFriendInfoPagerAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == BACKGROUD) {
                doBlur(data);
                XLog.e("这个data的大小为" + data.length);
            }
        }
    };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_friendinfo);
        ButterKnife.bind(this);
        headPath = getIntent().getStringExtra("uImg");
        nickName = getIntent().getStringExtra("uName");
        friendHeadNickName.setText(nickName);
        uId = getIntent().getIntExtra("uId", -1);
        if (uId == -1) {
            ToastUtil.showToast(this, "未知的错误产生了");
        } else {
            XLog.e(headPath + "   " + nickName);
            initView();
        }


    }

    private void initView() {
        friendPresenter = new FriendPresenterImpl(new FriendModelImpl(), this);
        friendPresenter.getFriendInfo(uId);
        imageViewBackground = new ImageView(this);
        getHeadImage(Constants.PHOTO_BASE_URL + headPath, friendHeadImage);
        getImageStream();
        XLog.e(Constants.PHOTO_BASE_URL + headPath);

    }

    /**
     * 获取头像
     */
    private void getHeadImage(String url, ImageView imageView) {
        ImageUtil.imageDisplay(url, imageView);
    }

    /**
     * 以头像作为背景 并做模糊化处理
     */
    private void doBlur(byte[] data) {
        final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        final BlurDrawable blurDrawable = new BlurDrawable(this, getResources(), bitmap);
        blurDrawable.setBlur(200);
        imageViewBackground.setImageDrawable(blurDrawable.getBlurDrawable());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            container.setBackground(imageViewBackground.getDrawable());
        }
    }

    @Override
    public void showFriendInfo(FriendInfo friendInfo) {

    }

    private void getImageStream() {
        Request request = new Request.Builder().url(Constants.PHOTO_BASE_URL + headPath).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    inputStream = response.body().byteStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    outputStream.close();
                    inputStream.close();
                    data = outputStream.toByteArray();
                    Message message = new Message();
                    message.what = BACKGROUD;
                    handler.sendMessage(message);
                }
            }
        });
    }

    @OnClick(R.id.turn_back)
    public void onClick(View v) {
        if (v.getId() == R.id.turn_back) {
            onBackPressed();
        }
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void searchUser(int type, String uName) {

    }

    @Override
    public void showUserfragment() {

    }

    @Override
    public void startFollow(int uId, int fId) {

    }

    @Override
    public void succeedFollow() {

    }

    @Override
    public void failedFollow() {

    }

    @Override
    public void showSearchUser(SearchInfo searchInfo) {

    }

    @Override
    public void getFollowed() {

    }
    static OnNumberChangedLisntener mLisntener;

    public static void setOnNumberChangedListener(OnNumberChangedLisntener listener){
        mLisntener=listener;
    }
    @Override
    public void getFollowedSucceed(FollowedInfo followedInfo) {
        EventBus.getDefault().postSticky(followedInfo);
        adapter = new MyFriendInfoPagerAdapter(getSupportFragmentManager(), this, followedInfo,uId);
        mLisntener.onFollowNumberChanged(followedInfo.getUsers().size());
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
                if (tab.getCustomView() != null) {
                    View tabView = (View) tab.getCustomView().getParent();
                    tabView.setTag(i);
                }
            }
        }
    }
}
